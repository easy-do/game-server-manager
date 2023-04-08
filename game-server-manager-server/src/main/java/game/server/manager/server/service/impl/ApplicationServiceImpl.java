package game.server.manager.server.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.command.CreateContainerResponse;
import game.server.manager.common.enums.AuditStatusEnum;
import game.server.manager.common.enums.ScopeEnum;
import game.server.manager.common.enums.ServerMessageTypeEnum;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.mode.socket.ApplicationVersionConfig;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.docker.model.CreateContainerDto;
import game.server.manager.server.dto.InstallApplicationDto;
import game.server.manager.server.dto.VersionConfData;
import game.server.manager.server.entity.ApplicationVersion;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.server.service.ApplicationVersionService;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.service.DockerContainerService;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.util.SessionUtils;
import game.server.manager.server.vo.DockerDetailsVo;
import game.server.manager.server.websocket.SocketSessionCache;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.ApplicationDto;
import game.server.manager.server.entity.Application;
import game.server.manager.server.qo.ApplicationQo;
import game.server.manager.server.vo.ApplicationVo;
import game.server.manager.server.mapstruct.ApplicationMapstruct;
import game.server.manager.server.mapper.ApplicationMapper;
import game.server.manager.server.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.websocket.Session;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * 应用信息Service层
 *
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
@Service
public class ApplicationServiceImpl extends BaseServiceImpl<Application, ApplicationQo, ApplicationVo, ApplicationDto, ApplicationMapper> implements ApplicationService {


    @Autowired
    private ApplicationVersionService applicationVersionService;
    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private DockerDetailsService dockerDetailsService;

    @Autowired
    private DockerContainerService dockerContainerService;

    @Override
    public void listSelect(LambdaQueryWrapper<Application> wrapper) {
        wrapper.select(Application::getId, Application::getApplicationName, Application::getStatus, Application::getAuthor);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<Application> wrapper) {

    }


    /**
     * 获取所有应用信息列表
     *
     * @return 应用信息
     */
    @Override
    public List<ApplicationVo> voList() {
        LambdaQueryWrapper<Application> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(Application::getCreateTime);
        listSelect(wrapper);
        return ApplicationMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


    /**
     * 分页条件查询应用信息列表
     *
     * @param mpBaseQo 查询条件封装
     * @return 应用信息
     */
    @Override
    public IPage<ApplicationVo> page(ApplicationQo mpBaseQo) {
        mpBaseQo.initInstance(Application.class);
        LambdaQueryWrapper<Application> wrapper = mpBaseQo.getWrapper().lambda();
        if (!isAdmin()) {
            wrapper.eq(Application::getCreateBy, getUserId());
        }
        return page(mpBaseQo.getPage(), wrapper).convert(ApplicationMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询应用信息
     *
     * @param id id
     * @return 应用信息
     */
    @Override
    public ApplicationVo info(Serializable id) {
        return ApplicationMapstruct.INSTANCE.entityToVo(getById(id));
    }


    /**
     * 新增应用信息
     *
     * @param applicationDto 数据传输对象
     * @return 结果
     */
    @Override
    @CacheInvalidate(name = "AppInfoService.storePage")
    public boolean add(ApplicationDto applicationDto) {
        Application entity = ApplicationMapstruct.INSTANCE.dtoToEntity(applicationDto);
        UserInfoVo user = getUser();
        entity.setCreateBy(user.getId());
        entity.setAuthor(user.getNickName());
        return save(entity);
    }

    /**
     * 修改应用信息
     *
     * @param applicationDto 数据传输对象
     * @return 结果
     */
    @Override
    @CacheInvalidate(name = "AppInfoService.storePage")
    public boolean edit(ApplicationDto applicationDto) {
        Application entity = ApplicationMapstruct.INSTANCE.dtoToEntity(applicationDto);
        Application oldData = getById(applicationDto.getId());
        if (Objects.isNull(oldData)) {
            throw ExceptionFactory.baseException("应用不存在");
        }
        if (!isAdmin() && oldData.getCreateBy() != getUserId()) {
            throw ExceptionFactory.baseException("无权操作");
        }
        entity.setUpdateBy(getUserId());
        entity.setStatus(AuditStatusEnum.DRAFT.getState());
        return updateById(entity);
    }

    /**
     * 批量删除应用信息
     *
     * @param id 需要删除的应用信息ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        Application oldData = getById(id);
        if (Objects.isNull(oldData)) {
            throw ExceptionFactory.baseException("应用不存在");
        }
        if (!isAdmin() && oldData.getCreateBy() != getUserId()) {
            throw ExceptionFactory.baseException("无权操作");
        }
        return removeById(id);
    }

    @Override
    @Cached(name = "AppInfoService.storePage", expire = 300, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60)
    public IPage<ApplicationVo> storePage(ApplicationQo applicationQo) {
        LambdaQueryWrapper<Application> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Application::getStatus, AuditStatusEnum.AUDIT_SUCCESS.getState());
        wrapper.eq(Application::getScope, 1);
        wrapper.orderByDesc(Application::getHeat);
        return page(applicationQo.startPage(), wrapper).convert(ApplicationMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public Object install(InstallApplicationDto installApplicationDto) {
        Long applicationId = installApplicationDto.getApplicationId();
        Application application = getById(applicationId);
        if (Objects.isNull(application)) {
            throw ExceptionFactory.baseException("应用不存在");
        }
        ApplicationVersion version = applicationVersionService.getById(installApplicationDto.getVersion());
        if (Objects.isNull(version)) {
            throw ExceptionFactory.baseException("应用版本不存在");
        }
        String clientId = installApplicationDto.getClientId();
        ClientInfo client = clientInfoService.getById(clientId);
        if (Objects.isNull(client)) {
            throw ExceptionFactory.baseException("客户端不存在");
        }
        Session clientSession = SocketSessionCache.getClientByClientId(client.getId());
        if(Objects.isNull(clientSession)){
            throw ExceptionFactory.baseException("客户端未连接");
        }
        //转换参数
        version.setConfData(installApplicationDto.getConfData());
        String messageId = UUID.fastUUID().toString();
        SessionUtils.sendSimpleServerMessage(clientSession, messageId, JSONUtil.toJsonStr(version), ServerMessageTypeEnum.INSTALL_APPLICATION);

        return "安装命令已下发";
    }
}
