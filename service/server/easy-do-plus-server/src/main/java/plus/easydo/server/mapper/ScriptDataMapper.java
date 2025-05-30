package plus.easydo.server.mapper;

import plus.easydo.server.entity.ScriptData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【script_data】的数据库操作Mapper
* @createDate 2022-05-26 18:30:13
* @Entity entity.server.plus.easydo.ScriptData
*/
@Mapper
public interface ScriptDataMapper extends BaseMapper<ScriptData> {

    /**
     * 增加热度
     *
     * @param id id
     * @author laoyu
     * @date 2022/6/17
     */
    void addHeat(Long id);
}




