package game.server.manager.uc.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.uc.dto.SysResourceDto;
import game.server.manager.uc.entity.SysResource;
import game.server.manager.uc.qo.SysResourceQo;
import game.server.manager.uc.vo.SysResourceVo;
import game.server.manager.uc.mapstruct.SysResourceMapstruct;
import game.server.manager.uc.mapper.SysResourceMapper;
import game.server.manager.uc.service.SysResourceService;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;


/**
 * 系统资源Service层
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource,SysResourceQo, SysResourceVo, SysResourceDto, SysResourceMapper> implements SysResourceService {


    @Override
    public void listSelect(LambdaQueryWrapper<SysResource> wrapper) {
        
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<SysResource> wrapper) {

    }


    /**
     * 获取所有系统资源列表
     *
     * @return 系统资源
     */
    @Override
    public List<SysResourceVo> voList() {
        LambdaQueryWrapper<SysResource> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(SysResource::getCreateTime);
        listSelect(wrapper);
        return SysResourceMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


     /**
     * 分页条件查询系统资源列表
     * 
     * @param mpBaseQo 查询条件封装
     * @return 系统资源
     */
    @Override
    public IPage<SysResourceVo> page(SysResourceQo mpBaseQo) {
        LambdaQueryWrapper<SysResource> wrapper = getWrapper();
        wrapper.orderByDesc(SysResource::getCreateTime);
        pageSelect(wrapper);
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(SysResourceMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询系统资源
     * 
     * @param id 系统资源ID
     * @return 系统资源
     */
    @Override
    public SysResourceVo info(Serializable id) {
        return SysResourceMapstruct.INSTANCE.entityToVo(getById(id));
    }




    /**
     * 新增系统资源
     * 
     * @param sysResourceDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(SysResourceDto sysResourceDto) {
        SysResource entity = SysResourceMapstruct.INSTANCE.dtoToEntity(sysResourceDto);
        entity.setCreateBy(getUserId());
        return save(entity);
    }

    /**
     * 修改系统资源
     * 
     * @param sysResourceDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(SysResourceDto sysResourceDto) {
        SysResource entity = SysResourceMapstruct.INSTANCE.dtoToEntity(sysResourceDto);
        return updateById(entity);
    }

    /**
     * 批量删除系统资源
     * 
     * @param id 需要删除的系统资源ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        return removeById(id);
    }

}
