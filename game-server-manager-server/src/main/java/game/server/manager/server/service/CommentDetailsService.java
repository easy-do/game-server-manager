package game.server.manager.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.CommentDetailsDto;
import game.server.manager.server.entity.CommentDetails;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.common.vo.CommentDetailsVo;

/**
* @author yuzhanfeng
* @description 针对表【comment_details(评论信息)】的数据库操作Service
* @createDate 2022-07-03 20:00:35
*/
public interface CommentDetailsService extends BaseService<CommentDetails, CommentDetailsVo, CommentDetailsDto> {

    IPage<CommentDetailsVo> managerPage(MpBaseQo mpBaseQo);
}
