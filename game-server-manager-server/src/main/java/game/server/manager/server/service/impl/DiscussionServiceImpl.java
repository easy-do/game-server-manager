package game.server.manager.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.DiscussionDto;
import game.server.manager.server.entity.Discussion;
import game.server.manager.common.enums.ProblemStateEnum;
import game.server.manager.server.mapstruct.DiscussionMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.service.DiscussionService;
import game.server.manager.server.mapper.CommonProblemMapper;
import game.server.manager.common.vo.DiscussionVo;
import game.server.manager.common.vo.UserInfoVo;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
* @author yuzhanfeng
* @description 针对表讨论交流的数据库操作Service实现
* @createDate 2022-07-03 20:00:32
*/
@Service
public class DiscussionServiceImpl extends BaseServiceImpl<Discussion, MpBaseQo<Discussion>, DiscussionVo, DiscussionDto, CommonProblemMapper>
    implements DiscussionService {

    public static final ConcurrentMap<String,IPage<DiscussionVo>> PAGE_CACHE = new ConcurrentHashMap<>();

    @Override
    public void listSelect(LambdaQueryWrapper<Discussion> wrapper) {

    }

    @Override
    public void pageSelect(LambdaQueryWrapper<Discussion> wrapper) {
        wrapper.select(Discussion::getId,Discussion::getStatus,Discussion::getTitle,Discussion::getDescription,Discussion::getCreateName,Discussion::getCreateTime,Discussion::getUpdateTime);
    }

    @Override
    public List<DiscussionVo> voList() {
        return null;
    }

    @Override
    public IPage<DiscussionVo> page(MpBaseQo<Discussion> mpBaseQo) {
        IPage<DiscussionVo> pageCache = PAGE_CACHE.get(mpBaseQo.getCurrentPage() + ":" + mpBaseQo.getPageSize());
        if(Objects.nonNull(pageCache)){
            return pageCache;
        }else {
            LambdaQueryWrapper<Discussion> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Discussion::getStatus, ProblemStateEnum.AUDIT.getState());
            wrapper.orderByDesc(Discussion::getCreateTime);
            pageSelect(wrapper);
            IPage<DiscussionVo> result = page(mpBaseQo.startPage(), wrapper).convert(DiscussionMapstruct.INSTANCE::entityToVo);
            PAGE_CACHE.put(mpBaseQo.getCurrentPage() + ":" + mpBaseQo.getPageSize(),result);
            return result;

        }
    }

    @Override
    public DiscussionVo info(Serializable id) {
        return DiscussionMapstruct.INSTANCE.entityToVo(getById(id));
    }

    @Override
    public boolean add(DiscussionDto discussionDto) {
        
        Discussion entity = DiscussionMapstruct.INSTANCE.dtoToEntity(discussionDto);
        entity.setCreateBy(getUserId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateName(getUser().getNickName());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setUpdateBy(getUserId());
        return save(entity);
    }

    @Override
    public boolean edit(DiscussionDto discussionDto) {
        
        Discussion entity = DiscussionMapstruct.INSTANCE.dtoToEntity(discussionDto);
        entity.setUpdateTime(LocalDateTime.now());
        entity.setUpdateBy(getUserId());
        LambdaQueryWrapper<Discussion> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            entity.setStatus(ProblemStateEnum.DRAFT.getState());
            wrapper.eq(Discussion::getCreateBy, getUserId());
        }
        wrapper.eq(Discussion::getId, entity.getId());
        return update(entity, wrapper);
    }

    @Override
    public boolean delete(Serializable id) {
        
        LambdaQueryWrapper<Discussion> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(Discussion::getCreateBy, getUserId());
        }
        wrapper.eq(Discussion::getId, id);
        return remove(wrapper);
    }

    @Override
    public IPage<DiscussionVo> managerPage(MpBaseQo<Discussion> mpBaseQo) {
        
        LambdaQueryWrapper<Discussion> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(Discussion::getCreateBy, getUserId());
        }
        wrapper.orderByDesc(Discussion::getCreateTime);
        pageSelect(wrapper);
       return page(mpBaseQo.startPage(), wrapper).convert(DiscussionMapstruct.INSTANCE::entityToVo);
    }

}




