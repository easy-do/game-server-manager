package game.server.manager.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.server.qo.CommentDetailsQo;
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
public interface CommentDetailsService extends BaseService<CommentDetails, CommentDetailsQo, CommentDetailsVo, CommentDetailsDto> {

    /**
     * 分页查询
     *
     * @param mpBaseQo mpBaseQo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<game.server.manager.common.vo.CommentDetailsVo>
     * @author laoyu
     * @date 2022/9/5
     */
    IPage<CommentDetailsVo> managerPage(MpBaseQo mpBaseQo);
}
