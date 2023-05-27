package plus.easydo.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.common.enums.AuditStatusEnum;
import plus.easydo.common.exception.ExceptionFactory;
import plus.easydo.server.dto.ApplicationVersionDto;
import plus.easydo.server.mapstruct.ApplicationVersionMapstruct;
import plus.easydo.server.vo.server.ApplicationVersionVo;
import plus.easydo.server.entity.Application;
import plus.easydo.server.mapper.ApplicationMapper;
import plus.easydo.web.base.BaseServiceImpl;
import plus.easydo.server.entity.ApplicationVersion;
import plus.easydo.server.qo.server.ApplicationVersionQo;
import plus.easydo.server.mapper.ApplicationVersionMapper;
import plus.easydo.server.service.ApplicationVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * 应用版本信息Service层
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
@Service
public class ApplicationVersionServiceImpl extends BaseServiceImpl<ApplicationVersion,ApplicationVersionQo, ApplicationVersionVo, ApplicationVersionDto, ApplicationVersionMapper> implements ApplicationVersionService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public void listSelect(LambdaQueryWrapper<ApplicationVersion> wrapper) {
        wrapper.select(ApplicationVersion::getId,ApplicationVersion::getApplicationName,ApplicationVersion::getStatus);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<ApplicationVersion> wrapper) {
    }


    /**
     * 获取所有应用版本信息列表
     *
     * @return 应用版本信息
     */
    @Override
    public List<ApplicationVersionVo> voList() {
        LambdaQueryWrapper<ApplicationVersion> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(ApplicationVersion::getCreateTime);
        listSelect(wrapper);
        return ApplicationVersionMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


     /**
     * 分页条件查询应用版本信息列表
     * 
     * @param mpBaseQo 查询条件封装
     * @return 应用版本信息
     */
    @Override
    public IPage<ApplicationVersionVo> page(ApplicationVersionQo mpBaseQo) {
        mpBaseQo.initInstance(ApplicationVersion.class);
        LambdaQueryWrapper<ApplicationVersion> wrapper = mpBaseQo.getWrapper().lambda();
        if(!isAdmin()){
            wrapper.eq(ApplicationVersion::getCreateBy,getUserId());
        }
        return page(mpBaseQo.getPage(), wrapper).convert(ApplicationVersionMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询应用版本信息
     * 
     * @param id id
     * @return 应用版本信息
     */
    @Override
    public ApplicationVersionVo info(Serializable id) {
        return ApplicationVersionMapstruct.INSTANCE.entityToVo(getById(id));
    }




    /**
     * 新增应用版本信息
     * 
     * @param applicationVersionDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(ApplicationVersionDto applicationVersionDto) {
        Application application = applicationMapper.selectById(applicationVersionDto.getApplicationId());
        if(Objects.isNull(application)){
            throw ExceptionFactory.bizException("应用不存在");
        }
        ApplicationVersion applicationVersion = getByApplicationIdAndVersion(application.getId(), applicationVersionDto.getVersion());
        if(Objects.nonNull(applicationVersion)){
            throw ExceptionFactory.bizException("版本已存在");
        }
        ApplicationVersion entity = ApplicationVersionMapstruct.INSTANCE.dtoToEntity(applicationVersionDto);
        entity.setCreateBy(getUserId());

        entity.setApplicationName(application.getApplicationName());
        return save(entity);
    }

    /**
     * 修改应用版本信息
     * 
     * @param applicationVersionDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(ApplicationVersionDto applicationVersionDto) {
        ApplicationVersion applicationVersion = getById(applicationVersionDto.getId());
        if(Objects.isNull(applicationVersion)){
            throw ExceptionFactory.baseException("版本不存在");
        }
        if(!isAdmin() && applicationVersion.getCreateBy() != getUserId()){
            throw ExceptionFactory.baseException("无权操作");
        }
        ApplicationVersion entity = ApplicationVersionMapstruct.INSTANCE.dtoToEntity(applicationVersionDto);
        entity.setStatus(AuditStatusEnum.DRAFT.getState());
        return updateById(entity);
    }

    /**
     * 批量删除应用版本信息
     * 
     * @param id 需要删除的应用版本信息ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        ApplicationVersion applicationVersion = getById(id);
        if(Objects.isNull(applicationVersion)){
            throw ExceptionFactory.baseException("版本不存在");
        }
        if(!isAdmin() && applicationVersion.getCreateBy() != getUserId()){
            throw ExceptionFactory.baseException("无权删除");
        }
        return removeById(id);
    }

    @Override
    public List<ApplicationVersionVo> versionList(Long applicationId) {
        LambdaQueryWrapper<ApplicationVersion> wrapper = getWrapper();
        wrapper.eq(ApplicationVersion::getApplicationId,applicationId);
        if(AuthorizationUtil.isLogin()){
            wrapper.and(wra->{
                wra.eq(ApplicationVersion::getStatus,AuditStatusEnum.AUDIT_SUCCESS.getState()).or().eq(ApplicationVersion::getCreateBy,AuthorizationUtil.getUserId());
            });
        }else {
            wrapper.eq(ApplicationVersion::getStatus,AuditStatusEnum.AUDIT_SUCCESS.getState());
        }
        return ApplicationVersionMapstruct.INSTANCE.entityToVo(baseMapper.selectList(wrapper));
    }

    @Override
    public ApplicationVersion getByApplicationIdAndVersion(Long applicationId, String version) {
        LambdaQueryWrapper<ApplicationVersion> wrapper = getWrapper();
        wrapper.eq(ApplicationVersion::getApplicationId,applicationId);
        wrapper.eq(ApplicationVersion::getVersion,version);
        return getOne(wrapper);
    }
}
