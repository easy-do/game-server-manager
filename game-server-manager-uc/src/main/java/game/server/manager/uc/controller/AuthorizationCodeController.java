package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.common.dto.AuthorizationConfigDto;
import game.server.manager.uc.entity.AuthorizationCode;
import game.server.manager.uc.service.AuthorizationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */
@RestController
@RequestMapping("/authCode")
public class AuthorizationCodeController {

    @Autowired
    private AuthorizationCodeService authorizationCodeService;

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/list")
    public R<List<AuthorizationCode>> list() {
        return DataResult.ok(BeanUtil.copyToList(authorizationCodeService.list(),AuthorizationCode.class));
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<AuthorizationCode> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(AuthorizationCode::getState);
        return MpResultUtil.buildPage(authorizationCodeService.page(mpBaseQo.startPage(),wrapper));
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/info/{id}")
    public R<AuthorizationCode> list(@PathVariable("id")String id) {
        return DataResult.ok(authorizationCodeService.getById(id));
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/update")
    @SaveLog(logType = "????????????", moduleName = "???????????????", description = "???????????????: ?1", expressions = {"#p1.id"},actionType = "??????")
    public R<Object> edit(@RequestBody AuthorizationCode authorizationCode) {
        return authorizationCodeService.updateById(authorizationCode)? DataResult.ok():DataResult.fail();
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/generateAuthCode")
    @SaveLog(logType = "????????????", moduleName = "???????????????", description = "??????????????? ?1 ???", expressions = {"#p1.genNum"},actionType = "??????")
    public R<Object> generateAuthorization(@RequestBody() AuthorizationConfigDto authorizationConfigDto) {
        boolean result = authorizationCodeService.generateAuthorization(authorizationConfigDto);
        return result? DataResult.ok():DataResult.fail();
    }

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "????????????", moduleName = "???????????????", description = "???????????????: ?1", expressions = {"#p1"}, actionType = "??????")
    public R<Object> delete(@PathVariable("id")String id) {
        return authorizationCodeService.removeById(id)? DataResult.ok():DataResult.fail();
    }
}
