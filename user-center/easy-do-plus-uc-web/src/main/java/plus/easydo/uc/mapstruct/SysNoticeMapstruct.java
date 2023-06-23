package plus.easydo.uc.mapstruct;

import plus.easydo.uc.entity.SysNotice;
import plus.easydo.uc.vo.SysNoticeVo;
import plus.easydo.uc.dto.SysNoticeDto;
import plus.easydo.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 通知公告转换类
 * @author yuzhanfeng
 * @date 2022-10-03 17:39:25
 */
@Mapper
public interface SysNoticeMapstruct extends BaseMapstruct<SysNotice, SysNoticeVo, SysNoticeDto> {

    SysNoticeMapstruct INSTANCE = Mappers.getMapper(SysNoticeMapstruct.class);

}
