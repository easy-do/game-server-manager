package game.server.manager.server.mapper;

import game.server.manager.server.entity.AppInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【app_info(APP信息)】的数据库操作Mapper
* @createDate 2022-05-22 17:44:07
* @Entity game.server.manager.server.entity.AppInfo
*/
@Mapper
public interface AppInfoMapper extends BaseMapper<AppInfo> {

    /**
     * 增加热度
     *
     * @param id id
     * @author laoyu
     * @date 2022/6/17
     */
    void addHeat(Long id);
}




