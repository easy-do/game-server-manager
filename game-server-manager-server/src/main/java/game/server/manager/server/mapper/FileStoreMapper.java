package game.server.manager.server.mapper;

import game.server.manager.server.entity.FileStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yuzhanfeng
* @description 针对表【file_store】的数据库操作Mapper
* @createDate 2022-07-14 19:36:26
* @Entity game.server.manager.server.entity.FileStore
*/
@Mapper
public interface FileStoreMapper extends BaseMapper<FileStore> {

}




