package game.server.manager.uc.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import game.server.manager.common.vo.SysDictTypeVo;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.uc.dto.SysDictDataDto;
import game.server.manager.uc.entity.SysDictData;
import game.server.manager.common.enums.StatusEnum;
import game.server.manager.uc.mapstruct.SysDictDataMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.service.SysDictDataService;
import game.server.manager.uc.mapper.SysDictDataMapper;
import game.server.manager.uc.service.SysDictTypeService;
import game.server.manager.common.vo.SysDictDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import game.server.manager.common.exception.BizException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* @author yuzhanfeng
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service实现
* @createDate 2022-07-22 10:19:20
*/
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictData,  MpBaseQo<SysDictData>, SysDictDataVo, SysDictDataDto, SysDictDataMapper>
    implements SysDictDataService{

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Override
    public void listSelect(LambdaQueryWrapper<SysDictData> wrapper) {
        wrapper.select(SysDictData::getId,SysDictData::getDictKey,SysDictData::getDictValue,SysDictData::getDictTypeId,SysDictData::getDictSort);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<SysDictData> wrapper) {

    }

    @Override
    public List<SysDictDataVo> voList() {
        LambdaQueryWrapper<SysDictData> wrapper = getWrapper();
        listSelect(wrapper);
        wrapper.eq(SysDictData::getStatus,StatusEnum.ENABLE);
        return SysDictDataMapstruct.INSTANCE.entityToVo(list(wrapper));
    }

    @Override
    public IPage<SysDictDataVo> page(MpBaseQo<SysDictData> mpBaseQo) {
        mpBaseQo.initInstance(SysDictData.class);
        return page(mpBaseQo.getPage(),mpBaseQo.getWrapper()).convert(SysDictDataMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public SysDictDataVo info(Serializable id) {
        return SysDictDataMapstruct.INSTANCE.entityToVo(getById(id));
    }

    @Override
    @CacheInvalidate(name = "SysDictDataService.listByCode")
    @CacheInvalidate(name = "SysDictDataService.getSingleDictData")
    @CacheInvalidate(name = "SysDictDataService.getDictDataMap")
    public boolean add(SysDictDataDto sysDictDataDto) {
        return save(SysDictDataMapstruct.INSTANCE.dtoToEntity(sysDictDataDto));
    }

    @Override
    @CacheInvalidate(name = "SysDictDataService.listByCode")
    @CacheInvalidate(name = "SysDictDataService.getSingleDictData")
    @CacheInvalidate(name = "SysDictDataService.getDictDataMap")
    public boolean edit(SysDictDataDto sysDictDataDto) {
        return updateById(SysDictDataMapstruct.INSTANCE.dtoToEntity(sysDictDataDto));
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
        SysDictData entity = SysDictData.builder().id(id).status(status).updateBy(updateUserId).build();
        return updateById(entity);
    }

    @Override
    @Cached(name = "SysDictDataService.listByCode", expire = 300, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60)
    public List<SysDictDataVo> listByCode(String dictCode) {
        Long dictTypeId = sysDictTypeService.getIdByCode(dictCode);
        LambdaQueryWrapper<SysDictData> wrapper = getWrapper();
        wrapper.select(SysDictData::getDictLabel,SysDictData::getDictSort,SysDictData::getDictKey,SysDictData::getDictValue,SysDictData::getDictValueType,SysDictData::getIsDefault);
        wrapper.eq(SysDictData::getDictTypeId,dictTypeId);
        wrapper.eq(SysDictData::getStatus, StatusEnum.ENABLE.getCode());
        return SysDictDataMapstruct.INSTANCE.entityToVo(list(wrapper));
    }

    @Override
    @Cached(name = "SysDictDataService.getSingleDictData", expire = 300, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60)
    public SysDictDataVo getSingleDictData(String dictCode, String dictDataKey) {
        Long dictTypeId = sysDictTypeService.getIdByCode(dictCode);
        LambdaQueryWrapper<SysDictData> wrapper = getWrapper();
        wrapper.select(SysDictData::getDictLabel,SysDictData::getDictSort,SysDictData::getDictKey,SysDictData::getDictValue,SysDictData::getDictValueType,SysDictData::getIsDefault);
        wrapper.eq(SysDictData::getDictTypeId,dictTypeId);
        wrapper.eq(SysDictData::getDictKey, dictDataKey);
        wrapper.eq(SysDictData::getStatus, StatusEnum.ENABLE.getCode());
        SysDictData sysDictData = getOne(wrapper);
        if(Objects.isNull(sysDictData)){
            throw new BizException("字典"+dictCode+":"+dictDataKey+"不存在");
        }
        return SysDictDataMapstruct.INSTANCE.entityToVo(sysDictData);
    }

    @Override
    @Cached(name = "SysDictDataService.getDictDataMap", expire = 300, cacheType = CacheType.BOTH)
    public Map<String, List<SysDictDataVo>> getDictDataMap() {
        List<SysDictTypeVo> typeList = sysDictTypeService.voList();
        Map<String,List<SysDictDataVo>> resultMap = Maps.newHashMapWithExpectedSize(typeList.size());
        typeList.forEach(sysDictTypeVo -> {
            String dictCode = sysDictTypeVo.getDictCode();
            List<SysDictDataVo> dataVoList = listByCode(dictCode);
            if(!dataVoList.isEmpty()){
                resultMap.put(dictCode,dataVoList);
            }
        });
        return resultMap;
    }
}




