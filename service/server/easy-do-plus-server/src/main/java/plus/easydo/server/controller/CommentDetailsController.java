package plus.easydo.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.server.dto.CommentDetailsDto;
import plus.easydo.server.entity.CommentDetails;
import plus.easydo.server.qo.server.CommentDetailsQo;
import plus.easydo.server.service.CommentDetailsService;
import plus.easydo.log.SaveLog;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import plus.easydo.common.vo.CommentDetailsVo;
import plus.easydo.web.base.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;


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
    @Override
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
    @Override
    public R<CommentDetailsVo> info(@PathVariable("id") Long id) {
        return super.info(id);
    }

    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "评论", description = "发起评论: ?1",expressions = {"#p1.content"}, actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) CommentDetailsDto commentDetailsDto) {
        return super.add(commentDetailsDto);
    }


    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "评论", description = "删除评论: ?1 ", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id") Long id) {
        return super.remove(id);
    }
}
