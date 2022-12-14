package game.server.manager.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.api.SysDictDataApi;
import game.server.manager.common.enums.ClientMessageTypeEnum;
import game.server.manager.common.exception.BizException;
import game.server.manager.common.utils.AppScriptUtils;
import game.server.manager.common.vo.ClientInfoVo;
import game.server.manager.common.vo.SysDictDataVo;
import game.server.manager.common.application.DeployParam;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.ClientInfoDto;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.ClientMessage;
import game.server.manager.server.entity.ExecuteLog;
import game.server.manager.server.entity.ServerInfo;
import game.server.manager.common.enums.AppStatusEnum;
import game.server.manager.server.mapstruct.ClientInfoMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.redis.ApplicationDeployListenerMessage;
import game.server.manager.redis.config.RedisStreamUtils;
import game.server.manager.server.service.AppEnvInfoService;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.mapper.ClientInfoMapper;
import game.server.manager.server.service.ClientMessageService;
import game.server.manager.server.service.ExecuteLogService;
import game.server.manager.server.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
* @author yuzhanfeng
* @description ????????????client_info(???????????????)?????????????????????Service??????
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
    private AppInfoService appInfoService;

    @Autowired
    private ClientMessageService clientMessageService;

    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Autowired
    private AppEnvInfoService appEnvInfoService;

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
                throw new BizException("??????????????????");
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
        //??????????????????
        ClientInfo clientInfo = getById(id);
        if(Objects.isNull(clientInfo)){
            throw new BizException("?????????????????????.");
        }
        LocalDateTime lastUpTime = clientInfo.getLastUpTime();
        if(Objects.isNull(lastUpTime) || LocalDateTimeUtil.between(lastUpTime,LocalDateTimeUtil.now(), ChronoUnit.MINUTES) > 1){
            removeById(id);
            basePublishEventServer.publishClientUninstallEvent((String) id);
            clientMessageService.removeByClientId((String) id);
            executeLogService.removeByApplicationId(id);
            return true;
        }
        
        String unInstallScriptId = getUnInstallScriptId();
        //??????????????????
        ExecuteLog executeLog = ExecuteLog.builder()
                .applicationId((String) id)
                .applicationName(clientInfo.getClientName())
                .createTime(LocalDateTime.now())
                .executeState(AppStatusEnum.QUEUE.getDesc())
                .createBy(getUserId())
                .build();
        executeLogService.save(executeLog);
        //?????????????????????
        JSONObject env = new JSONObject();
        env.put("CLIENT_ID",id);
        DeployParam deployParam = DeployParam.builder()
                .applicationId((String) id)
                .appScriptId(unInstallScriptId)
                .userId(String.valueOf(getUserId()))
                .env(env)
                .isClient(true)
                .uninstall(true)
                .logId(String.valueOf(executeLog.getId()))
                .build();
        ClientMessage messageEntity = ClientMessage.builder()
                .clientId((String) id)
                .message(JSON.toJSONString(deployParam))
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
            throw new BizException("?????????????????????.");
        }
        Long serverId = clientInfo.getServerId();
        if(Objects.isNull(serverId)){
            throw new BizException("??????????????????.");
        }
        if(!serverInfoService.exists(serverId)){
            throw new BizException("?????????????????????.");
        }
        
        JSONObject env = new JSONObject();
        env.put("CLIENT_ID",id);
        DeployParam deployParam = DeployParam.builder()
                .applicationId(id)
                .appScriptId(getInstallScriptId())
                .userId(String.valueOf(getUserId()))
                .env(env)
                .isClient(true)
                .build();
        //??????????????????
        ExecuteLog entity = ExecuteLog.builder()
                .applicationId(id)
                .applicationName(clientInfo.getClientName())
                .createTime(LocalDateTime.now())
                .executeState(AppStatusEnum.QUEUE.getDesc())
                .createBy(getUserId())
                .build();
        executeLogService.save(entity);
        //??????????????????
        deployParam.setLogId(String.valueOf(entity.getId()));
        redisStreamUtils.add(ApplicationDeployListenerMessage.DEFAULT_STREAM_NAME, BeanUtil.beanToMap(deployParam));
        return true;
    }

    @Override
    public String getInstallCmd(String id) {
        String clientInstallCmd = sysDictDataService.getSingleDictData("system_config", "client_install_cmd")
                .getData().getDictValue();
        String appId = sysDictDataService.getSingleDictData("system_config", "client_app_id").getData().getDictValue();
        AppInfo appInfo = appInfoService.getById(appId);
        JSONObject env = new JSONObject();
        env.put("APP_ID", appId);
        env.put("CLIENT_ID", id);
        env.put("CLIENT_VERSION", appInfo.getVersion());
        String installScriptId = getInstallScriptId();
        return clientInstallCmd + " "+ AppScriptUtils.generateExecShellEnvs(env,appEnvInfoService.getVoListByScriptId(Long.valueOf(installScriptId)));
    }

    @Override
    public boolean exists(String clientId) {
        LambdaQueryWrapper<ClientInfo> wrapper = getWrapper();
        wrapper.eq(ClientInfo::getId,clientId);
        return baseMapper.exists(wrapper);
    }

    private String getInstallScriptId(){
        SysDictDataVo clientScriptDictData = sysDictDataService.getSingleDictData("system_config", "client_install_script").getData();
        if(Objects.isNull(clientScriptDictData)){
            throw new BizException("??????????????????????????????.");
        }
        return clientScriptDictData.getDictValue();
    }
    private String getUnInstallScriptId(){
        SysDictDataVo clientScriptDictData = sysDictDataService.getSingleDictData("system_config", "client_uninstall_script").getData();
        if(Objects.isNull(clientScriptDictData)){
            throw new BizException("????????????????????????????????????.");
        }
        return clientScriptDictData.getDictValue();
    }
}




