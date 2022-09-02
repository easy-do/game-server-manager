package game.server.manager.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.uc.entity.DictData;
import game.server.manager.uc.mapper.DictDataMapper;
import game.server.manager.uc.service.DictDataService;
import org.springframework.stereotype.Service;


/**
* @author yuzhanfeng
* @description 针对表【dict_data(字典数据)】的数据库操作Service实现
* @createDate 2022-05-05 16:15:14
*/
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData>
    implements DictDataService {

    @Override
    public DictData getByKey(String key) {
        LambdaQueryWrapper<DictData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DictData::getDictKey,key);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean setByKey(String key, String value) {
        LambdaQueryWrapper<DictData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DictData::getDictKey,key);
        DictData dictData = DictData.builder().dictValue(value).build();
        return update(dictData,wrapper);
    }
}




