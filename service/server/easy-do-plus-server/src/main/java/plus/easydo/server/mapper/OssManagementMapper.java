package plus.easydo.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import plus.easydo.server.entity.OssManagement;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 存储管理的数据库操作Mapper
 * @Entity plus.easydo.push.oss.entity.OssManagement
 */
@Mapper
public interface OssManagementMapper extends BaseMapper<OssManagement> {
    
}
