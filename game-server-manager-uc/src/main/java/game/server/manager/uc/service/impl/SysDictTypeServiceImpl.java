package game.server.manager.uc.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.enums.StatusEnum;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.uc.dto.SysDictTypeDto;
import game.server.manager.uc.entity.SysDictType;
import game.server.manager.uc.mapstruct.SysDictTypeMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.service.SysDictTypeService;
import game.server.manager.uc.mapper.SysDictTypeMapper;
import game.server.manager.common.vo.SysDictTypeVo;
import org.springframework.stereotype.Service;
import game.server.manager.common.exception.BizException;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
* @author yuzhanfeng
* @description 针对表【sys_dict_type(字典类型表)】的数据库操作Service实现
* @createDate 2022-07-22 10:19:24
*/
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictType, MpBaseQo<SysDictType>, SysDictTypeVo, SysDictTypeDto, SysDictTypeMapper>
    implements SysDictTypeService{

    @Override
    public void listSelect(LambdaQueryWrapper<SysDictType> wrapper) {
        wrapper.select(SysDictType::getDictName,SysDictType::getId,SysDictType::getDictCode,SysDictType::getStatus);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<SysDictType> wrapper) {

    }

    @Override
    public List<SysDictTypeVo> voList() {
        LambdaQueryWrapper<SysDictType> wrapper = getWrapper();
        listSelect(wrapper);
        wrapper.eq(SysDictType::getStatus, StatusEnum.ENABLE);
        return SysDictTypeMapstruct.INSTANCE.entityToVo(list(wrapper));
    }

    @Override
    public IPage<SysDictTypeVo> page(MpBaseQo<SysDictType> mpBaseQo) {
        return page(mpBaseQo.startPage(),mpBaseQo.buildSearchWrapper()).convert(SysDictTypeMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public SysDictTypeVo info(Serializable id) {
        return SysDictTypeMapstruct.INSTANCE.entityToVo(getById(id));
    }

    @Override
    @CacheInvalidate(name = "SysDictDataService.listByCode")
    @CacheInvalidate(name = "SysDictDataService.getSingleDictData")
    @CacheInvalidate(name = "SysDictDataService.getDictDataMap")
    public boolean add(SysDictTypeDto sysDictTypeDto) {
        return save(SysDictTypeMapstruct.INSTANCE.dtoToEntity(sysDictTypeDto));
    }

    @Override
    @CacheInvalidate(name = "SysDictDataService.listByCode")
    @CacheInvalidate(name = "SysDictDataService.getSingleDictData")
    @CacheInvalidate(name = "SysDictDataService.getDictDataMap")
    public boolean edit(SysDictTypeDto sysDictTypeDto) {
        return updateById(SysDictTypeMapstruct.INSTANCE.dtoToEntity(sysDictTypeDto));
    }

    @Override
    @CacheInvalidate(name = "SysDictDataService.listByCode")
    @CacheInvalidate(name = "SysDictDataService.getSingleDictData")
    @CacheInvalidate(name = "SysDictDataService.getDictDataMap")
    public boolean delete(Serializable id) {
        return removeById(id);
    }

    @Override
    @CacheInvalidate(name = "SysDictDataService.listByCode")
    @CacheInvalidate(name = "SysDictDataService.getSingleDictData")
    @CacheInvalidate(name = "SysDictDataService.getDictDataMap")
    public boolean changeStatus(ChangeStatusDto changeStatusDto) {
        Long id = changeStatusDto.getId();
        Integer status = changeStatusDto.getStatus();
        Long updateUserId = changeStatusDto.getUpdateUserId();
        SysDictType entity = SysDictType.builder().id(id).status(status).updateBy(updateUserId).build();
        return updateById(entity);
    }

    @Override
    public Long getIdByCode(String dictCode) {
        LambdaQueryWrapper<SysDictType> wrapper = getWrapper();
        wrapper.select(SysDictType::getId);
        wrapper.eq(SysDictType::getDictCode,dictCode);
        SysDictType dictType = getOne(wrapper);
        if(Objects.isNull(dictType)){
            throw new BizException("字典不存在");
        }
        return dictType.getId();
    }
}




