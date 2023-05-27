package plus.easydo.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.server.dto.DiscussionDto;
import plus.easydo.web.base.BaseService;
import plus.easydo.server.entity.Discussion;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.vo.DiscussionVo;

/**
* @author yuzhanfeng
* @description 针对表讨论交流的数据库操作Service
* @createDate 2022-07-03 20:00:32
*/
public interface DiscussionService extends BaseService<Discussion, MpBaseQo<Discussion>, DiscussionVo, DiscussionDto> {

    /**
     * 管理分页
     *
     * @param mpBaseQo mpBaseQo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<plus.easydo.push.server.vo.DiscussionVo>
     * @author laoyu
     * @date 2022/7/8
     */
    IPage<DiscussionVo> managerPage(MpBaseQo<Discussion> mpBaseQo);

}
