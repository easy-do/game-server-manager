package game.server.manager.server.mapper;

import game.server.manager.server.entity.AppScript;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【app_script】的数据库操作Mapper
* @createDate 2022-05-26 18:30:13
* @Entity game.server.manager.server.entity.AppScript
*/
@Mapper
public interface AppScriptMapper extends BaseMapper<AppScript> {

    /**
     * 增加热度
     *
     * @param id id
     * @author laoyu
     * @date 2022/6/17
     */
    void addHeat(Long id);
}




