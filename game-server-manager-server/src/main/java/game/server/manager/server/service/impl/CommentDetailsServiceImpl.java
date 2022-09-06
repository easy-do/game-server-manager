package game.server.manager.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import game.server.manager.api.UserInfoApi;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.server.qo.CommentDetailsQo;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.server.dto.CommentDetailsDto;
import game.server.manager.server.entity.CommentDetails;
import game.server.manager.server.entity.Discussion;
import game.server.manager.server.mapstruct.CommentDetailsMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.redis.config.RedisUtils;
import game.server.manager.server.service.CommentDetailsService;
import game.server.manager.server.mapper.CommentDetailsMapper;
import game.server.manager.server.service.DiscussionService;
import game.server.manager.common.vo.CommentDetailsVo;
import game.server.manager.common.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import game.server.manager.common.exception.BizException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yuzhanfeng
 * @description 针对表【comment_details(评论信息)】的数据库操作Service实现
 * @createDate 2022-07-03 20:00:35
 */
@Service
public class CommentDetailsServiceImpl extends BaseServiceImpl<CommentDetails, CommentDetailsQo, CommentDetailsVo, CommentDetailsDto, CommentDetailsMapper> implements CommentDetailsService {

    @Autowired
    private UserInfoApi userInfoService;

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Autowired
    private RedisUtils<Object> redisUtils;

    @Override
    public void listSelect(LambdaQueryWrapper<CommentDetails> wrapper) {

    }

    @Override
    public void pageSelect(LambdaQueryWrapper<CommentDetails> wrapper) {

    }

    @Override
    public List<CommentDetailsVo> voList() {
        return null;
    }

    @Override
    public IPage<CommentDetailsVo> page(CommentDetailsQo commentDetailsQo) {
        LambdaQueryWrapper<CommentDetails> wrapper = commentDetailsQo.buildSearchWrapper();
        wrapper.orderByAsc(CommentDetails::getCreateTime);
        Long discussionId = commentDetailsQo.getDiscussionId();
        if (Objects.isNull(discussionId)) {
            return new Page<>();
        }
        //获得所有该主题的二级评论
        List<CommentDetailsVo> allChildren = baseMapper.selectList(getWrapper().eq(CommentDetails::getBusinessId, discussionId).ne(CommentDetails::getParentId, 0))
                .stream().map(CommentDetailsMapstruct.INSTANCE::entityToVo).collect(Collectors.toList());
        //获取第一级评论 递归出父子结构
        wrapper.eq(CommentDetails::getBusinessId, discussionId);
        wrapper.eq(CommentDetails::getParentId, 0);
        return baseMapper.selectPage(commentDetailsQo.startPage(), wrapper).convert(entityClass -> {
            CommentDetailsVo vo = CommentDetailsMapstruct.INSTANCE.entityToVo(entityClass);
            String avatar = userInfoService.avatar(vo.getUserId()).getData();
            vo.setUserAvatar(avatar);
            List<CommentDetailsVo> rootChildrenList = allChildren.stream().filter(children -> children.getParentId().equals(vo.getId())).collect(Collectors.toList());
            allChildren.removeAll(rootChildrenList);
            vo.setChildren(rootChildrenList);
            return vo;
        });
    }


    @Override
    public CommentDetailsVo info(Serializable id) {
        LambdaQueryWrapper<CommentDetails> wrapper = getWrapper();
        if (!isAdmin()) {
            wrapper.eq(CommentDetails::getUserId, getUserId());
        }
        wrapper.eq(CommentDetails::getId, id);
        return CommentDetailsMapstruct.INSTANCE.entityToVo(baseMapper.selectOne(wrapper));
    }

    @Override
    public boolean add(CommentDetailsDto commentDetailsDto) {
        boolean result;
        Long userId = getUserId();
        checkCommentCount(userId);
        CommentDetails entity = CommentDetailsMapstruct.INSTANCE.dtoToEntity(commentDetailsDto);
        Long commentId = commentDetailsDto.getCommentId();
        Long businessId = entity.getBusinessId();
        Discussion discussion = discussionService.getById(businessId);
        if (Objects.isNull(discussion)) {
            throw new BizException("主题不存在");
        }
        entity.setUserId(userId);
        entity.setUserName(getUser().getNickName());
        entity.setCreateTime(LocalDateTime.now());
        if (commentId != 0) {
            //回复的是评论
            CommentDetails toCommentDetails = getById(commentId);
            if (Objects.isNull(toCommentDetails)) {
                throw new BizException("评论不存在");
            }
            //获取被回复的用户信息
            Long toUserId = toCommentDetails.getUserId();
            //不允许回复自己的评论
            if (toUserId.equals(userId)) {
                throw new BizException("不能回复自己的评论");
            }
            UserInfoVo toUser = userInfoService.getUserInfo(toUserId).getData();
            if (Objects.isNull(toUser)) {
                throw new BizException("用户已不存在");
            }
            entity.setToUserId(toUserId);
            entity.setToUserName(toUser.getNickName());

        } else {
            //回复的是主题
            Long discussionUserId = discussion.getCreateBy();
            UserInfoVo toUser = userInfoService.getUserInfo(discussionUserId).getData();
            if (Objects.isNull(toUser)) {
                throw new BizException("用户已不存在");
            }
            entity.setToUserId(toUser.getId());
            entity.setToUserName(toUser.getNickName());
        }
        result = save(entity);
        //只要需要接收通知的人不是自己，那么就发送通知
        if (result && !entity.getToUserId().equals(userId)){
            //发送收到回复通知
            basePublishEventServer.publishCommentEvent(entity.getToUserId(),entity.getToUserName(),discussion.getTitle());
        }
        if(result){
            cacheCommentCount(getUserId());
        }
        return result;
    }

    private void checkCommentCount(Long userId){
        Object result = redisUtils.get(SystemConstant.PREFIX + SystemConstant.USER_COMMENT + userId);
        if(Objects.nonNull(result)){
            throw new BizException("回复过快，请稍等一分钟");
        }
    }

    private void cacheCommentCount(Long userId){
        redisUtils.set(SystemConstant.PREFIX +SystemConstant.USER_COMMENT+userId,1,1, TimeUnit.MINUTES);
    }

    @Override
    public boolean edit(CommentDetailsDto commentDetailsDto) {
        return false;
    }

    @Override
    public boolean delete(Serializable id) {
        
        LambdaQueryWrapper<CommentDetails> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(CommentDetails::getUserId, getUserId());
        }
        wrapper.eq(CommentDetails::getId, id);
        return remove(wrapper);
    }

    @Override
    public IPage<CommentDetailsVo> managerPage(MpBaseQo<CommentDetails> mpBaseQo) {
        
        LambdaQueryWrapper<CommentDetails> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(CommentDetails::getUserId, getUserId());
        }
        wrapper.orderByDesc(CommentDetails::getCreateTime);
        return baseMapper.selectPage(mpBaseQo.startPage(), wrapper).convert(CommentDetailsMapstruct.INSTANCE::entityToVo);
    }
}




