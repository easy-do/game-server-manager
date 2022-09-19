package game.server.manager.uc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.constant.SystemConstant;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.common.vo.UserPointsVo;
import game.server.manager.uc.dto.UserPointsOperationDto;
import game.server.manager.uc.entity.UserPointsLog;
import game.server.manager.uc.qo.UserPointsQo;
import game.server.manager.uc.service.UserPointsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 用户积分相关
 * @date 2022/7/12
 */
@RestController
@RequestMapping("/userPoints")
public class UserPointsController {

    @Autowired
    private UserPointsLogService userPointsLogService;


    /**
     * 分页查询
     *
     * @param userPointsQo userPointsQo
     * @return game.server.manager.server.result.MPDataResult
     * @author laoyu
     * @date 2022/7/6
     */
    @SaCheckLogin
    @PostMapping("/page")
    public MpDataResult page(@RequestBody UserPointsQo userPointsQo){
        LambdaQueryWrapper<UserPointsLog> wrapper = userPointsQo.buildSearchWrapper();
        if(Objects.isNull(userPointsQo.getUserId())){
            return MpResultUtil.empty();
        }
        wrapper.orderByDesc(UserPointsLog::getCreateTime);
        IPage<UserPointsLog> page = userPointsLogService.page(userPointsQo.startPage(),wrapper);
        return MpResultUtil.buildPage(page, UserPointsVo.class);
    }



    /**
     * 对指定用户积分进行操作
     *
     * @param userPointsOperationDto userPointsOperationDto
     * @return game.server.manager.server.result.MPDataResult
     * @author laoyu
     * @date 2022/7/29
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/userPointsOperation")
    public R<Object> userPointsOperation(@Validated @RequestBody UserPointsOperationDto userPointsOperationDto){
        return userPointsLogService.userPointsOperation(userPointsOperationDto)? DataResult.ok():DataResult.fail();
    }


}
