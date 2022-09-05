package game.server.manager.server.service.impl;

import cn.hutool.extra.ssh.JschUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jcraft.jsch.Session;
import game.server.manager.server.application.DeploymentServer;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.common.exception.BizException;
import game.server.manager.server.dto.ServerInfoDto;
import game.server.manager.server.entity.ServerInfo;
import game.server.manager.server.mapstruct.ServerInfoMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.service.ApplicationInfoService;
import game.server.manager.server.service.ServerInfoService;
import game.server.manager.server.mapper.ServerInfoMapper;
import game.server.manager.common.vo.ServerInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import game.server.manager.common.result.DataResult;

import java.io.Serializable;
import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【server_info(服务器信息)】的数据库操作Service实现
* @createDate 2022-05-19 19:29:55
*/
@Service
public class ServerInfoServiceImpl extends BaseServiceImpl<ServerInfo, MpBaseQo, ServerInfoVo, ServerInfoDto,ServerInfoMapper>
    implements ServerInfoService{

    @Autowired
    private ApplicationInfoService applicationInfoService;

    @Override
    public long countByUserId(Long userId) {
        LambdaQueryWrapper<ServerInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ServerInfo::getUserId,userId);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public boolean exists(Long serverId) {
        LambdaQueryWrapper<ServerInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ServerInfo::getId,serverId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public void listSelect(LambdaQueryWrapper<ServerInfo> wrapper) {
        wrapper.select(ServerInfo::getId,ServerInfo::getServerName);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<ServerInfo> wrapper) {
        wrapper.select(ServerInfo::getId,ServerInfo::getServerName,ServerInfo::getAddress,ServerInfo::getUserName);
    }

    @Override
    public List<ServerInfoVo> voList() {
        LambdaQueryWrapper<ServerInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ServerInfo::getUserId,getUserId());
        }
        listSelect(wrapper);
        return ServerInfoMapstruct.INSTANCE.entityToVo(list(wrapper));
    }

    @Override
    public IPage<ServerInfoVo> page(MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<ServerInfo> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(ServerInfo::getUserId, getUserId());
        }
        wrapper.orderByDesc(ServerInfo::getCreateTime);
        pageSelect(wrapper);
        return page(mpBaseQo.startPage(), wrapper).convert(ServerInfoMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public ServerInfoVo info(Serializable id) {
        LambdaQueryWrapper<ServerInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ServerInfo::getUserId,getUserId());
        }
        wrapper.eq(ServerInfo::getId,id);
        return ServerInfoMapstruct.INSTANCE.entityToVo(getOne(wrapper));
    }

    @Override
    public boolean add(ServerInfoDto serverInfoDto) {
        //校验授权信息
        checkAuthorization("serverAdd");
        ServerInfo entity = ServerInfoMapstruct.INSTANCE.dtoToEntity(serverInfoDto);
        entity.setUserId(getUserId());
        return save(entity);
    }

    @Override
    public boolean edit(ServerInfoDto serverInfoDto) {
        //校验授权信息
        checkAuthorization("serverEdit");
        ServerInfo entity = ServerInfoMapstruct.INSTANCE.dtoToEntity(serverInfoDto);
        LambdaQueryWrapper<ServerInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ServerInfo::getUserId,getUserId());
        }
        wrapper.eq(ServerInfo::getId,entity.getId());
        return update(entity,wrapper);
    }

    @Override
    public boolean delete(Serializable id) {
        //校验授权信息
        checkAuthorization("serverDel");
        long count = applicationInfoService.countByServerId(String.valueOf(id));
        if(count >= 1){
            DataResult.fail("拒绝删除,已绑定应用");
        }
        LambdaQueryWrapper<ServerInfo> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(ServerInfo::getUserId, getUserId());
        }
        wrapper.eq(ServerInfo::getId,id);
        return remove(wrapper);
    }

    @Override
    public boolean test(ServerInfoDto serverInfoDto) {
        String address = serverInfoDto.getAddress();
        String port = serverInfoDto.getPort();
        String userName = serverInfoDto.getUserName();
        String password = serverInfoDto.getPassword();
        try{
            Session session = JschUtil.openSession(address, Integer.parseInt(port), userName, password, 3000);
            if(session.isConnected()){
                JschUtil.close(session);
            }
            return true;
        }catch (Exception exception){
            String exceptionMessage = DeploymentServer.convertExceptionMessage(exception);
            throw new BizException(exceptionMessage);
        }
    }
}




