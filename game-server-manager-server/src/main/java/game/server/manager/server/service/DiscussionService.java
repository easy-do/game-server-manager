package game.server.manager.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.DiscussionDto;
import game.server.manager.server.entity.Discussion;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.common.vo.DiscussionVo;

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
     * @return com.baomidou.mybatisplus.core.metadata.IPage<game.server.manager.server.vo.DiscussionVo>
     * @author laoyu
     * @date 2022/7/8
     */
    IPage<DiscussionVo> managerPage(MpBaseQo<Discussion> mpBaseQo);

}
