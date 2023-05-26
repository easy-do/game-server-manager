package plus.easydo.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.server.qo.server.CommentDetailsQo;
import plus.easydo.server.dto.CommentDetailsDto;
import plus.easydo.web.base.BaseService;
import plus.easydo.server.entity.CommentDetails;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.vo.CommentDetailsVo;

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
     * @return com.baomidou.mybatisplus.core.metadata.IPage<vo.plus.easydo.common.CommentDetailsVo>
     * @author laoyu
     * @date 2022/9/5
     */
    IPage<CommentDetailsVo> managerPage(MpBaseQo<CommentDetails> mpBaseQo);
}
