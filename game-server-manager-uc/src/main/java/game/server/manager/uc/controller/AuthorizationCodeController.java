package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

    @SaCheckRole("super_admin")
    @GetMapping("/list")
    public R<List<AuthorizationCode>> list() {
        return DataResult.ok(BeanUtil.copyToList(authorizationCodeService.list(),AuthorizationCode.class));
    }

    @SaCheckRole("super_admin")
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<AuthorizationCode> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(AuthorizationCode::getState);
        return MpResultUtil.buildPage(authorizationCodeService.page(mpBaseQo.startPage(),wrapper));
    }

    @SaCheckRole("super_admin")
    @GetMapping("/info/{id}")
    public R<AuthorizationCode> list(@PathVariable("id")String id) {
        return DataResult.ok(authorizationCodeService.getById(id));
    }

    @SaCheckRole("super_admin")
    @PostMapping("/edit")
    @SaveLog(logType = "操作日志", moduleName = "授权码管理", description = "编辑授权码: ?1", expressions = {"#p1.id"},actionType = "编辑")
    public R<Object> edit(@RequestBody AuthorizationCode authorizationCode) {
        return authorizationCodeService.updateById(authorizationCode)? DataResult.ok():DataResult.fail();
    }

    @SaCheckRole("super_admin")
    @PostMapping("/generateAuthCode")
    @SaveLog(logType = "操作日志", moduleName = "授权码管理", description = "生成授权码 ?1 个", expressions = {"#p1.genNum"},actionType = "添加")
    public R<Object> generateAuthorization(@RequestBody() AuthorizationConfigDto authorizationConfigDto) {
        boolean result = authorizationCodeService.generateAuthorization(authorizationConfigDto);
        return result? DataResult.ok():DataResult.fail();
    }

    @SaCheckRole("super_admin")
    @GetMapping("/delete/{id}")
    @SaveLog(logType = "操作日志", moduleName = "授权码管理", description = "删除授权码: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> delete(@PathVariable("id")String id) {
        return authorizationCodeService.removeById(id)? DataResult.ok():DataResult.fail();
    }
}
