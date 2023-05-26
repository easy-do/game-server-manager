package plus.easydo.uc.controller;

import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.stp.StpUtil;
import plus.easydo.common.vo.UserInfoVo;
import plus.easydo.uc.entity.UserInfo;
import plus.easydo.uc.mapstruct.UserInfoMapstruct;
import plus.easydo.uc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/11
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/avatar/{id}")
    public R<String> avatar(@PathVariable("id")Long id) {
        return DataResult.ok(userInfoService.avatar(id));
    }

    @GetMapping("/getUserInfo")
    public R<UserInfoVo> getUserInfo(@RequestParam("userId") Long userId) {
        if(!StpUtil.isLogin()){
            // 不是用户请求的则校验 Id-Token 身份凭证
            SaSameUtil.checkCurrentRequestToken();
        }
        UserInfo userInfo = userInfoService.getById(userId);
        return DataResult.ok(UserInfoMapstruct.INSTANCE.entityToVo(userInfo));
    }

    @GetMapping("/count")
    public R<Long> count() {
        return DataResult.ok(userInfoService.count());
    }
}
