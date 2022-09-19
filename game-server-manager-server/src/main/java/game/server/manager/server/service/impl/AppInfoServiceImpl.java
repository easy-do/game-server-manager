package game.server.manager.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.server.qo.AppInfoQo;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.AppInfoDto;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.common.enums.AuditStatusEnum;
import game.server.manager.common.enums.ScopeEnum;
import game.server.manager.server.mapstruct.AppInfoMapstruct;
import game.server.manager.server.service.AppInfoService;
import game.server.manager.server.mapper.AppInfoMapper;
import game.server.manager.server.service.ApplicationInfoService;
import game.server.manager.common.vo.AppInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import game.server.manager.common.result.DataResult;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
* @author yuzhanfeng
* @description 针对表【app_info(APP信息)】的数据库操作Service实现
* @createDate 2022-05-22 17:44:07
*/
@Service
public class AppInfoServiceImpl extends BaseServiceImpl<AppInfo, AppInfoQo, AppInfoVo, AppInfoDto, AppInfoMapper> implements AppInfoService{

    public static final ConcurrentMap<String,IPage<AppInfo>> STORE_PAGE_CACHE = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationInfoService applicationInfoService;


    @Override
    public void listSelect(LambdaQueryWrapper<AppInfo> wrapper) {
        wrapper.select(AppInfo::getId, AppInfo::getAppName, AppInfo::getAuthor, AppInfo::getDescription);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<AppInfo> wrapper) {

    }

    @Override
    public List<AppInfoVo> voList() {
        LambdaQueryWrapper<AppInfo> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(AppInfo::getCreateBy, getUserId());
            wrapper.eq(AppInfo::getState, 1);
            wrapper.or().eq(AppInfo::getAppScope, ScopeEnum.PUBLIC);
        }
        wrapper.orderByDesc(AppInfo::getCreateTime);
        listSelect(wrapper);
        return AppInfoMapstruct.INSTANCE.entityToVo(list(wrapper));
    }

    @Override
    public IPage<AppInfoVo> page(AppInfoQo mpBaseQo) {
        mpBaseQo.initInstance(AppInfo.class);
        LambdaQueryWrapper<AppInfo> wrapper = mpBaseQo.getWrapper().lambda();
        if (!isAdmin()) {
            wrapper.eq(AppInfo::getCreateBy, getUserId());
        }
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(AppInfoMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public AppInfoVo info(Serializable id) {
        LambdaQueryWrapper<AppInfo> wrapper = getWrapper();
        if (!isAdmin()) {
            wrapper.eq(AppInfo::getCreateBy, getUserId());
        }
        wrapper.eq(AppInfo::getId, id);
        return AppInfoMapstruct.INSTANCE.entityToVo(baseMapper.selectOne(wrapper));
    }

    @Override
    public boolean add(AppInfoDto appInfoDto) {
        //校验授权信息
        checkAuthorization("appAdd");
        AppInfo entity = AppInfoMapstruct.INSTANCE.dtoToEntity(appInfoDto);
        entity.setCreateBy(getUserId());
        entity.setAuthor(getUser().getNickName());
        return save(entity);
    }

    @Override
    public boolean edit(AppInfoDto appInfoDto) {
        //校验授权信息
        checkAuthorization("appEdit");
        AppInfo entity = AppInfoMapstruct.INSTANCE.dtoToEntity(appInfoDto);
        entity.setUpdateBy(getUserId());
        LambdaQueryWrapper<AppInfo> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(AppInfo::getCreateBy, getUserId());
        }
        wrapper.eq(AppInfo::getId, entity.getId());
        return update(entity, wrapper);
    }

    @Override
    public boolean delete(Serializable id) {
        //校验授权信息
        checkAuthorization("appDel");
        long count = applicationInfoService.countByAppId(id);
        if (count >= 1) {
            DataResult.fail("拒绝删除,已有应用绑定");
        }
        LambdaQueryWrapper<AppInfo> wrapper = getWrapper();
        if (!isAdmin()) {
            wrapper.eq(AppInfo::getCreateBy, getUserId());
        }
        wrapper.eq(AppInfo::getId, id);
        return remove(wrapper);
    }


    @Override
    public long countByUserId(Long userId) {
        LambdaQueryWrapper<AppInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppInfo::getCreateBy,userId);
        return count(wrapper);
    }

    @Override
    public void addHeat(Long id) {
        baseMapper.addHeat(id);
    }

    @Override
    public boolean exists(Long id) {
        LambdaQueryWrapper<AppInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppInfo::getId,id);
        return baseMapper.exists(wrapper);
    }

    @Override
    public IPage<AppInfo> storePage(AppInfoQo mpBaseQo) {
        IPage<AppInfo> cachePage = STORE_PAGE_CACHE.get(mpBaseQo.getCurrentPage() + ":" + mpBaseQo.getPageSize());
        if(Objects.nonNull(cachePage)){
            return cachePage;
        }else {
            LambdaQueryWrapper<AppInfo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(AppInfo::getIsAudit, AuditStatusEnum.AUDIT_SUCCESS.getState());
            wrapper.eq(AppInfo::getAppScope, ScopeEnum.PUBLIC.getScope()).or().eq(AppInfo::getAppScope, ScopeEnum.SUBSCRIBE.getScope());
            wrapper.orderByDesc(AppInfo::getHeat);
            IPage<AppInfo> result = page(mpBaseQo.startPage(), wrapper);
            STORE_PAGE_CACHE.put(mpBaseQo.getCurrentPage() + ":" + mpBaseQo.getPageSize(),result);
            return result;
        }
    }
}




