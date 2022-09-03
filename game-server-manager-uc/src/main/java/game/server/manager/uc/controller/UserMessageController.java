package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.id.SaIdUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.dto.UserMessageDto;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.common.vo.UserMessageVo;
import game.server.manager.uc.entity.UserMessage;
import game.server.manager.uc.mapstruct.UserMessageMapstruct;
import game.server.manager.uc.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/10
 */
@RestController
@RequestMapping("/userMessage")
public class UserMessageController {

    @Autowired
    private UserMessageService userMessageService;

    @SaCheckLogin
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<UserMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserMessage::getUserId, AuthorizationUtil.getUserId());
        wrapper.orderByDesc(UserMessage::getCreateTime);
        IPage<UserMessage> page = userMessageService.page(mpBaseQo.startPage(), wrapper);
        return MpResultUtil.buildPage(page, UserMessageVo.class);
    }

    @SaCheckLogin
    @GetMapping("/count")
    public R<Long> page() {
        LambdaQueryWrapper<UserMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserMessage::getUserId, AuthorizationUtil.getUserId());
        wrapper.eq(UserMessage::getStatus, 0);
        long count = userMessageService.count(wrapper);
        return DataResult.ok(count);
    }

    @SaCheckLogin
    @GetMapping("/readMessage/{id}")
    public R<Long> readMessage(@PathVariable Long id) {
        Long userId = AuthorizationUtil.getUserId();
        LambdaQueryWrapper<UserMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserMessage::getUserId, userId);
        wrapper.eq(UserMessage::getStatus, 0);
        wrapper.eq(UserMessage::getId, id);
        UserMessage entity = UserMessage.builder().build();
        entity.setStatus(1);
        return userMessageService.update(entity, wrapper) ? DataResult.ok() : DataResult.fail();
    }

    @SaCheckLogin
    @GetMapping("/readAllMessage")
    public R<Long> readAllMessage() {
        Long userId = AuthorizationUtil.getUserId();
        LambdaQueryWrapper<UserMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserMessage::getUserId, userId);
        wrapper.eq(UserMessage::getStatus, 0);
        UserMessage entity = UserMessage.builder().build();
        entity.setStatus(1);
        return userMessageService.update(entity, wrapper) ? DataResult.ok() : DataResult.fail();
    }

    @SaCheckLogin
    @GetMapping("/cleanAllMessage")
    public R<Long> cleanAllMessage() {
        Long userId = AuthorizationUtil.getUserId();
        LambdaQueryWrapper<UserMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserMessage::getUserId, userId);
        return userMessageService.remove(wrapper) ? DataResult.ok() : DataResult.okMsg("没有任何消息。");
    }

    @SaCheckLogin
    @PostMapping("/insert")
    public R<Long> insert(@RequestBody UserMessageDto userMessageDto) {
        if (!StpUtil.isLogin()) {
            // 不是用户请求的则校验 Id-Token 身份凭证
            SaIdUtil.checkCurrentRequestToken();
        }
        UserMessage userMessage = UserMessageMapstruct.INSTANCE.dtoToEntity(userMessageDto);
        return userMessageService.save(userMessage) ? DataResult.ok() : DataResult.fail();
    }

}
