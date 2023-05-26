package plus.easydo.uc.mapper;


import plus.easydo.uc.entity.SysNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 通知公告的数据库操作Mapper
 * @Entity entity.plus.easydo.uc.SysNotice
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
    
}
