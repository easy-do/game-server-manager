package plus.easydo.uc.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import plus.easydo.common.vo.SysDictTypeVo;
import plus.easydo.web.base.BaseServiceImpl;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.uc.dto.SysDictDataDto;
import plus.easydo.uc.entity.SysDictData;
import plus.easydo.common.enums.StatusEnum;
import plus.easydo.uc.mapstruct.SysDictDataMapstruct;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.uc.service.SysDictDataService;
import plus.easydo.uc.mapper.SysDictDataMapper;
import plus.easydo.uc.service.SysDictTypeService;
import plus.easydo.common.vo.SysDictDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.easydo.common.exception.BizException;

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
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper,SysDictData> implements SysDictDataService{

    @Autowired
    private SysDictTypeService sysDictTypeService;

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
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.lambdaQuery();
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
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.lambdaQuery();
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




