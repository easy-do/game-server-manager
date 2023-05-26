package plus.easydo.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import plus.easydo.api.SysDictDataApi;
import plus.easydo.common.application.ExecScriptParam;
import plus.easydo.common.enums.AppStatusEnum;
import plus.easydo.common.enums.ClientMessageTypeEnum;
import plus.easydo.common.enums.ServerMessageTypeEnum;
import plus.easydo.common.exception.ExceptionFactory;
import plus.easydo.common.mode.ClientData;
import plus.easydo.common.utils.ScriptDataUtils;
import plus.easydo.common.vo.ClientInfoVo;
import plus.easydo.common.vo.SysDictDataVo;
import plus.easydo.event.BasePublishEventServer;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.redis.RedisStreamUtils;
import plus.easydo.server.dto.ClientInfoDto;
import plus.easydo.server.entity.ClientInfo;
import plus.easydo.server.entity.ClientMessage;
import plus.easydo.server.entity.ExecuteLog;
import plus.easydo.server.entity.ServerInfo;
import plus.easydo.server.mapper.ClientInfoMapper;
import plus.easydo.server.mapstruct.ClientInfoMapstruct;
import plus.easydo.server.redis.ExecScriptListenerMessage;
import plus.easydo.server.service.ScriptEnvDataService;
import plus.easydo.server.service.ClientInfoService;
import plus.easydo.server.service.ClientMessageService;
import plus.easydo.server.service.ExecuteLogService;
import plus.easydo.server.service.ServerInfoService;
import plus.easydo.server.util.server.SessionUtils;
import plus.easydo.server.websocket.SocketSessionCache;
import plus.easydo.web.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
* @author yuzhanfeng
* @description 针对表【client_info(客户端信息)】的数据库操作Service实现
* @createDate 2022-08-04 19:22:22
*/
@Service
public class ClientInfoServiceImpl extends BaseServiceImpl<ClientInfo, MpBaseQo<ClientInfo>, ClientInfoVo, ClientInfoDto, ClientInfoMapper>
    implements ClientInfoService{


    @Autowired
    private ServerInfoService serverInfoService;

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private RedisStreamUtils<Object> redisStreamUtils;

    @Autowired
    private SysDictDataApi sysDictDataService;

    @Autowired
    private ClientMessageService clientMessageService;

    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Autowired
    private ScriptEnvDataService scriptEnvDataService;

    @Override
    public void listSelect(LambdaQueryWrapper<ClientInfo> wrapper) {
        wrapper.select(ClientInfo::getId, ClientInfo::getClientName);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<ClientInfo> wrapper) {
    }

    @Override
    public List<ClientInfoVo> voList() {
        
        LambdaQueryWrapper<ClientInfo> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(ClientInfo::getCreateBy, getUserId());
            wrapper.eq(ClientInfo::getStatus, AppStatusEnum.DEPLOYMENT_SUCCESS.getDesc());
        }
        wrapper.orderByDesc(ClientInfo::getCreateTime);
        listSelect(wrapper);
        return ClientInfoMapstruct.INSTANCE.entityToVo(baseMapper.selectList(wrapper));
    }

    @Override
    public IPage<ClientInfoVo> page(MpBaseQo<ClientInfo> mpBaseQo) {
        mpBaseQo.initInstance(ClientInfo.class);
        LambdaQueryWrapper<ClientInfo> wrapper = mpBaseQo.getWrapper().lambda();
        if(!isAdmin()){
            wrapper.eq(ClientInfo::getCreateBy,getUserId());
        }
        return baseMapper.selectPage(mpBaseQo.getPage(),wrapper).convert(ClientInfoMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public ClientInfoVo info(Serializable id) {
        
        LambdaQueryWrapper<ClientInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ClientInfo::getCreateBy,getUserId());
        }
        wrapper.eq(ClientInfo::getId,id);
        return ClientInfoMapstruct.INSTANCE.entityToVo(baseMapper.selectOne(wrapper));
    }

    @Override
    public boolean add(ClientInfoDto clientInfoDto) {
        ClientInfo entity = ClientInfoMapstruct.INSTANCE.dtoToEntity(clientInfoDto);
        if(Objects.nonNull(entity.getServerId())){
            ServerInfo serverInfo = serverInfoService.getById(entity.getServerId());
            if(Objects.isNull(serverInfo)){
                throw ExceptionFactory.bizException("服务器不存在");
            }
            entity.setServerName(serverInfo.getServerName());
        }
        
        entity.setCreateBy(getUserId());
        entity.setUserName(getUser().getNickName());
        String clientId = DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_PATTERN) + "-" + UUID.randomUUID().toString(false);
        entity.setId(clientId);
        RSA rsa = new RSA();
        String privateKey = rsa.getPrivateKeyBase64();
        String publicKey = rsa.getPublicKeyBase64();
        entity.setPrivateKey(privateKey);
        entity.setPublicKey(publicKey);
        entity.setStatus(AppStatusEnum.NO_DEPLOYMENT.getDesc());
        return save(entity);
    }

    @Override
    public boolean edit(ClientInfoDto clientInfoDto) {
        return false;
    }

    @Override
    public boolean delete(Serializable id) {
        //执行卸载脚本
        ClientInfo clientInfo = getById(id);
        if(Objects.isNull(clientInfo)){
            throw ExceptionFactory.bizException("客户端已不存在.");
        }
        LocalDateTime lastUpTime = clientInfo.getLastUpTime();
        if(Objects.isNull(lastUpTime) || LocalDateTimeUtil.between(lastUpTime,LocalDateTimeUtil.now(), ChronoUnit.MINUTES) > 1){
            removeById(id);
            basePublishEventServer.publishClientUninstallEvent((String) id);
            clientMessageService.removeByClientId((String) id);
            executeLogService.removeByDeviceId(id);
            return true;
        }
        
        String unInstallScriptId = getUnInstallScriptId();
        //创建执行记录
        ExecuteLog executeLog = ExecuteLog.builder()
                .deviceId((String) id)
                .deviceName(clientInfo.getClientName())
                .createTime(LocalDateTime.now())
                .executeState(AppStatusEnum.QUEUE.getDesc())
                .createBy(getUserId())
                .build();
        executeLogService.save(executeLog);
        //生成客户端消息
        JSONObject env = new JSONObject();
        env.put("CLIENT_ID",id);
        ExecScriptParam execScriptParam = ExecScriptParam.builder()
                .deviceId((String) id)
                .scriptId(unInstallScriptId)
                .userId(String.valueOf(getUserId()))
                .env(env)
                .executeLogId(String.valueOf(executeLog.getId()))
                .build();
        ClientMessage messageEntity = ClientMessage.builder()
                .clientId((String) id)
                .message(JSON.toJSONString(execScriptParam))
                .createBy(getUserId())
                .updateBy(getUserId())
                .messageType(ClientMessageTypeEnum.UNINSTALL.getCode())
                .build();
        return clientMessageService.save(messageEntity);
    }

    @Override
    public boolean onlineInstall(String id) {
        ClientInfo clientInfo = getById(id);
        if(Objects.isNull(clientInfo)){
            throw ExceptionFactory.bizException("客户端已不存在.");
        }
        Long serverId = clientInfo.getServerId();
        if(Objects.isNull(serverId)){
            throw ExceptionFactory.bizException("未绑定服务器.");
        }
        if(!serverInfoService.exists(serverId)){
            throw ExceptionFactory.bizException("服务器已不存在.");
        }
        
        JSONObject env = new JSONObject();
        env.put("CLIENT_ID",id);
        ExecScriptParam execScriptParam = ExecScriptParam.builder()
                .deviceId(id)
                .scriptId(getInstallScriptId())
                .userId(String.valueOf(getUserId()))
                .env(env)
                .build();
        //创建执行记录
        ExecuteLog entity = ExecuteLog.builder()
                .deviceId(id)
                .deviceName(clientInfo.getClientName())
                .createTime(LocalDateTime.now())
                .executeState(AppStatusEnum.QUEUE.getDesc())
                .createBy(getUserId())
                .build();
        executeLogService.save(entity);
        //发布订阅消息
        execScriptParam.setExecuteLogId(String.valueOf(entity.getId()));
        redisStreamUtils.add(ExecScriptListenerMessage.DEFAULT_STREAM_NAME, BeanUtil.beanToMap(execScriptParam));
        return true;
    }

    @Override
    public String getInstallCmd(String id) {
        ClientInfo client = getById(id);
        if(Objects.isNull(client)){
            throw ExceptionFactory.bizException("客户端不存在.");
        }
        String clientInstallCmd = sysDictDataService.getSingleDictData("system_config", "client_install_cmd")
                .getData().getDictValue();
        JSONObject env = new JSONObject();
        env.put("CLIENT_ID", id);
        env.put("PUBLIC_KEY", client.getPublicKey());
        String installScriptId = getInstallScriptId();
        return clientInstallCmd + " "+ ScriptDataUtils.generateExecShellEnvs(env, scriptEnvDataService.getVoListByScriptId(Long.valueOf(installScriptId)));
    }

    @Override
    public boolean exists(String clientId) {
        LambdaQueryWrapper<ClientInfo> wrapper = getWrapper();
        wrapper.eq(ClientInfo::getId,clientId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public void updateHeartbeat(ClientData clientData) {
        String clientId = clientData.getClientId();
        Session clientSession = SocketSessionCache.getClientByClientId(clientId);
        assert clientSession != null;
        if(CharSequenceUtil.isEmpty(clientId)){
            SessionUtils.sendErrorServerMessage(clientSession,clientSession.getId(),"客户端标识为空");
        }
        ClientInfo dbClientInfo = getById(clientId);
        if(Objects.isNull(dbClientInfo)){
            SessionUtils.sendErrorServerMessage(clientSession,clientSession.getId(),"客户端不存在");
        }
        if (CharSequenceUtil.isEmpty(clientData.getIp())) {
            SessionUtils.sendErrorServerMessage(clientSession,clientSession.getId(),"未知的服务器ip");
        }
        if (CharSequenceUtil.isEmpty(clientData.getVersion())) {
            SessionUtils.sendErrorServerMessage(clientSession,clientSession.getId(),"未知的版本信息");
        }
        ClientInfo clientInfo = ClientInfo.builder()
                .id(clientId)
                .status("最近在线")
                .clientData(com.alibaba.fastjson2.JSON.toJSONString(clientData))
                .lastUpTime(LocalDateTime.now())
                .status(AppStatusEnum.DEPLOYMENT_SUCCESS.getDesc()).build();
        updateById(clientInfo);
        SessionUtils.sendSimpleServerMessage(clientSession,clientSession.getId(),"success", ServerMessageTypeEnum.HEARTBEAT);
    }

    private String getInstallScriptId(){
        SysDictDataVo clientScriptDictData = sysDictDataService.getSingleDictData("system_config", "client_install_script").getData();
        if(Objects.isNull(clientScriptDictData)){
            throw ExceptionFactory.bizException("客户端脚本配置不存在.");
        }
        return clientScriptDictData.getDictValue();
    }
    private String getUnInstallScriptId(){
        SysDictDataVo clientScriptDictData = sysDictDataService.getSingleDictData("system_config", "client_uninstall_script").getData();
        if(Objects.isNull(clientScriptDictData)){
            throw ExceptionFactory.bizException("客户端卸载脚本配置不存在.");
        }
        return clientScriptDictData.getDictValue();
    }
}




