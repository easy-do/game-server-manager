package game.server.manager.uc.mapstruct;

import game.server.manager.uc.entity.SysNotice;
import game.server.manager.uc.vo.SysNoticeVo;
import game.server.manager.uc.dto.SysNoticeDto;
import game.server.manager.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.io.Serializable;
import java.util.List;



/**
 * 通知公告转换类
 * @author yuzhanfeng
 * @date 2022-10-03 17:39:25
 */
@Mapper
public interface SysNoticeMapstruct extends BaseMapstruct<SysNotice, SysNoticeVo, SysNoticeDto> {

    SysNoticeMapstruct INSTANCE = Mappers.getMapper(SysNoticeMapstruct.class);

}