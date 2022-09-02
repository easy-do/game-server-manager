package game.server.manager.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import game.server.manager.uc.entity.DictData;

/**
* @author yuzhanfeng
* @description 针对表【dict_data(字典数据)】的数据库操作Service
* @createDate 2022-05-05 16:15:14
*/
public interface DictDataService extends IService<DictData> {

    /**
     * 根据key获取参数
     *
     * @param key key
     * @return game.server.manager.server.entity.DictData
     * @author laoyu
     * @date 2022/5/31
     */
    DictData getByKey(String key);

    /**
     * 根据key设置参数
     *
     * @param key   key
     * @param value value
     * @return
     * @author laoyu
     * @date 2022/5/31
     */
    boolean setByKey(String key, String value);
}
