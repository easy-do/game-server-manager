package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.server.qo.CommentDetailsQo;
import game.server.manager.server.dto.CommentDetailsDto;
import game.server.manager.server.entity.CommentDetails;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.server.service.CommentDetailsService;
import game.server.manager.common.vo.CommentDetailsVo;
import game.server.manager.web.base.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.R;
import game.server.manager.common.vaild.Insert;


/**
 * @author laoyu
 * @version 1.0
 * @description 评论相关接口
 * @date 2022/7/4
 */
@RestController
@RequestMapping("/commentDetails")
public class CommentDetailsController extends BaseController<CommentDetailsService, CommentDetails, Long, CommentDetailsQo, CommentDetailsVo, CommentDetailsDto> {
    

    @PostMapping("/page")
    public MpDataResult page(@RequestBody CommentDetailsQo commentDetailsQo) {
        return super.page(commentDetailsQo);
    }



    @SaCheckLogin
    @PostMapping("/managerPage")
    public MpDataResult managerPage(@RequestBody MpBaseQo mpBaseQo) {
        return MpResultUtil.buildPage(baseService.managerPage(mpBaseQo));
    }

    @SaCheckLogin
    @RequestMapping("/info/{id}")
    public R<CommentDetailsVo> info(@PathVariable("id") Long id) {
        return super.info(id);
    }

    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "评论", description = "发起评论: ?1",expressions = {"#p1.content"}, actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) CommentDetailsDto commentDetailsDto) {
        return super.add(commentDetailsDto);
    }


    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/delete/{id}")
    @SaveLog(logType = "操作日志", moduleName = "评论", description = "删除评论: ?1 ", expressions = {"#p1"}, actionType = "删除")
    public R<Object> delete( @PathVariable("id") Long id) {
        return super.delete(id);
    }
}
