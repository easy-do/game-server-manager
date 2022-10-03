package game.server.manager.uc.mapper;


import game.server.manager.uc.entity.SysNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description 通知公告的数据库操作Mapper
 * @Entity game.server.manager.uc.entity.SysNotice
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
    
}
