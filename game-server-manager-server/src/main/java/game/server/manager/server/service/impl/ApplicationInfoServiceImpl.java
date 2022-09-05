package game.server.manager.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.common.application.DeployParam;
import game.server.manager.common.enums.ClientMessageTypeEnum;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.ApplicationInfoDto;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.server.entity.AppScript;
import game.server.manager.server.entity.ApplicationInfo;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.ClientMessage;
import game.server.manager.server.entity.ExecuteLog;
import game.server.manager.server.entity.ServerInfo;
import game.server.manager.common.enums.AppStatusEnum;
import game.server.manager.common.enums.DeviceTypeEnum;
import game.server.manager.server.mapstruct.ApplicationInfoMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.redis.ApplicationDeployListenerMessage;
import game.server.manager.redis.config.RedisStreamUtils;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.server.service.AppScriptService;
import game.server.manager.server.service.ApplicationInfoService;
import game.server.manager.server.mapper.ApplicationInfoMapper;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.service.ClientMessageService;
import game.server.manager.server.service.ExecuteLogService;
import game.server.manager.server.service.ServerInfoService;
import game.server.manager.common.vo.ApplicationInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import game.server.manager.common.exception.BizException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
* @author yuzhanfeng
* @description 针对表【application_info(应用信息)】的数据库操作Service实现
* @createDate 2022-05-20 10:29:54
*/
@Service
public class ApplicationInfoServiceImpl extends BaseServiceImpl<ApplicationInfo, MpBaseQo, ApplicationInfoVo, ApplicationInfoDto,ApplicationInfoMapper>
    implements ApplicationInfoService{

    @Autowired
    private ServerInfoService serverInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppScriptService appScriptService;

    @Autowired
    private RedisStreamUtils<Object> redisStreamUtils;

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private ClientMessageService clientMessageService;

    @Override
    public long countByUserId(Long userId) {
            LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ApplicationInfo::getUserId,userId);
            return baseMapper.selectCount(wrapper);
    }

    @Override
    public void setAllBlackByUserId(Long id) {
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ApplicationInfo::getUserId,id);
        ApplicationInfo entity = ApplicationInfo.builder().isBlack(1).build();
        baseMapper.update(entity,wrapper);
    }

    @Override
    public boolean exist(String id, Long userId) {
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ApplicationInfo::getApplicationId,id);
        wrapper.eq(ApplicationInfo::getUserId,userId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public boolean deployment(DeployParam deployParam) {
        //校验授权信息
        checkAuthorization("deploy");
        deployParam.setUserId(String.valueOf(getUserId()));
        String appId = deployParam.getApplicationId();
        ApplicationInfo application = baseMapper.selectById(appId);
        ServerInfo serverInfo = null;
        ClientInfo clientInfo = null;
        if(Objects.isNull(application)){
            throw new BizException("应用"+appId+"已不存在.");
        }
        if(!appInfoService.exists(application.getAppId())){
            throw new BizException("APP不存在.");
        }
        Integer deviceType = application.getDeviceType();
        if(deviceType == DeviceTypeEnum.SERVER.getType()){
            serverInfo = serverInfoService.getById(Long.valueOf(application.getDeviceId()));
            if(Objects.isNull(serverInfo)){
                throw new BizException("目标服务器不存在.");
            }
        }else {
            clientInfo = clientInfoService.getById(application.getDeviceId());
            if(Objects.isNull(clientInfo)){
                throw new BizException("目标客户端不存在.");
            }
        }
        AppScript appScript = appScriptService.getById(deployParam.getAppScriptId());
        if(Objects.isNull(appScript)){
            throw new BizException("脚本不存在.");
        }
        //创建执行记录
        ExecuteLog entity = ExecuteLog.builder()
                .applicationId(application.getApplicationId())
                .applicationName(application.getApplicationName())
                .applicationId(application.getApplicationId()).applicationName(application.getApplicationName())
                .appId(application.getAppId()).appName(application.getAppName())
                .scriptId(appScript.getId()).scriptName(appScript.getScriptName())
                .createTime(LocalDateTime.now())
                .createBy(Long.parseLong(deployParam.getUserId()))
                .build();
        if(deviceType == DeviceTypeEnum.SERVER.getType()){
            entity.setDeviceId(String.valueOf(serverInfo.getId()));
            entity.setDeviceName(serverInfo.getServerName());
            entity.setExecuteState(AppStatusEnum.QUEUE.getDesc());
        }else {
            entity.setDeviceId(clientInfo.getId());
            entity.setDeviceName(clientInfo.getClientName());
            entity.setExecuteState(AppStatusEnum.WAIT_CLIENT.getDesc());
        }
        boolean result = executeLogService.save(entity);
        if(result){
            deployParam.setLogId(String.valueOf(entity.getId()));
            if(deviceType == DeviceTypeEnum.SERVER.getType()){
                //发布订阅消息
                redisStreamUtils.add(ApplicationDeployListenerMessage.DEFAULT_STREAM_NAME, BeanUtil.beanToMap(deployParam));
                return true;
            }else{
                //生成客户端消息
                ClientMessage messageEntity = ClientMessage.builder()
                        .clientId(application.getDeviceId())
                        .message(JSON.toJSONString(deployParam))
                        .createBy(getUserId())
                        .updateBy(getUserId())
                        .messageType(ClientMessageTypeEnum.SCRIPT.getCode())
                        .build();
                return clientMessageService.save(messageEntity);
            }
        }
        return false;
    }

    @Override
    public long countByServerId(String id) {
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ApplicationInfo::getDeviceId,id);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public long countByAppId(Serializable id) {
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ApplicationInfo::getAppId,id);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public void listSelect(LambdaQueryWrapper<ApplicationInfo> wrapper) {
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<ApplicationInfo> wrapper) {
        wrapper.select(ApplicationInfo::getApplicationId,ApplicationInfo::getApplicationName,
                ApplicationInfo::getAppId,ApplicationInfo::getAppName,
                ApplicationInfo::getDeviceId,ApplicationInfo::getDeviceName,
                ApplicationInfo::getLastUpTime,ApplicationInfo::getStatus);
    }

    @Override
    public List<ApplicationInfoVo> voList() {
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ApplicationInfo::getUserId,getUserId());
        }
        wrapper.orderByDesc(ApplicationInfo::getLastUpTime);
        listSelect(wrapper);
        return ApplicationInfoMapstruct.INSTANCE.entityToVo(baseMapper.selectList(wrapper));
    }

    @Override
    public IPage<ApplicationInfoVo> page(MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ApplicationInfo::getUserId,getUserId());
        }
        wrapper.orderByDesc(ApplicationInfo::getLastUpTime);
        pageSelect(wrapper);
        return baseMapper.selectPage(mpBaseQo.startPage(),wrapper).convert(ApplicationInfoMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public ApplicationInfoVo info(Serializable id) {
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ApplicationInfo::getUserId,getUserId());
        }
        wrapper.eq(ApplicationInfo::getApplicationId,id);
        return ApplicationInfoMapstruct.INSTANCE.entityToVo(baseMapper.selectOne(wrapper));
    }

    @Override
    public boolean add(ApplicationInfoDto applicationInfoDto) {
        //校验授权信息
        checkAuthorization("applicationAdd");
        ApplicationInfo entity = ApplicationInfoMapstruct.INSTANCE.dtoToEntity(applicationInfoDto);
        AppInfo appInfo = appInfoService.getById(entity.getAppId());
        if(Objects.isNull(appInfo)){
            throw new BizException("app不存在");
        }
        entity.setAppName(appInfo.getAppName());
        Integer deviceType = entity.getDeviceType();
        if(deviceType == DeviceTypeEnum.SERVER.getType()){
        ServerInfo serverInfo = serverInfoService.getById(entity.getDeviceId());
        if(Objects.isNull(serverInfo)){
            throw new BizException("服务不存在");
        }
        entity.setDeviceName(serverInfo.getServerName());
        }
        if(deviceType == DeviceTypeEnum.CLIENT.getType()){
            ClientInfo client = clientInfoService.getById(entity.getDeviceId());
            if(Objects.isNull(client)){
                throw new BizException("客户端不存在");
            }
            entity.setDeviceName(client.getClientName());
        }
        String applicationId = DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_PATTERN) + "-" + UUID.randomUUID().toString(false);
        entity.setUserId(getUserId());
        RSA rsa = new RSA();
        String privateKey = rsa.getPrivateKeyBase64();
        String publicKey = rsa.getPublicKeyBase64();
        entity.setApplicationId(applicationId);
        entity.setPrivateKey(privateKey);
        entity.setPublicKey(publicKey);
        entity.setStatus(AppStatusEnum.NO_DEPLOYMENT.getDesc());
        return save(entity);
    }

    @Override
    public boolean edit(ApplicationInfoDto applicationInfoDto) {
        //校验授权信息
        checkAuthorization("applicationEdit");
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ApplicationInfo::getUserId,getUserId());
        }
        wrapper.eq(ApplicationInfo::getApplicationId,applicationInfoDto.getApplicationId());
        ApplicationInfo app = getOne(wrapper);
        if(Objects.isNull(app)){
            throw new BizException("应用不存在或不属于你");
        }
        String state = app.getStatus();
        if( CharSequenceUtil.equals(state, AppStatusEnum.NO_DEPLOYMENT.getDesc()) || CharSequenceUtil.equals(state, AppStatusEnum.UNINSTALL.getDesc())){
            ApplicationInfo entity = ApplicationInfoMapstruct.INSTANCE.dtoToEntity(applicationInfoDto);
            entity.setUpdateTime(LocalDateTime.now());
            return update(entity,wrapper);
        }
        throw new BizException("应用处于无法更改状态,请先卸载。");
    }

    @Override
    public boolean delete(Serializable id) {
        //校验授权信息
        checkAuthorization("applicationDel");
        LambdaQueryWrapper<ApplicationInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ApplicationInfo::getUserId,getUserId());
        }
        wrapper.eq(ApplicationInfo::getApplicationId,id);
        ApplicationInfo app = baseMapper.selectOne(wrapper);
        if(Objects.isNull(app)){
            throw new BizException("应用不存在或不属于你");
        }
        String state = app.getStatus();
        if( CharSequenceUtil.equals(state, AppStatusEnum.NO_DEPLOYMENT.getDesc()) || CharSequenceUtil.equals(state, AppStatusEnum.UNINSTALL.getDesc())){
            boolean result = remove(wrapper);
            if(result){
                executeLogService.removeByApplicationId(id);
            }
            return result;
        }
        throw  new BizException("应用处于无法删除状态,请先卸载。");
    }
}




