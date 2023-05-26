package plus.easydo.uc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import plus.easydo.common.vo.UserPointsVo;
import plus.easydo.uc.dto.UserPointsOperationDto;
import plus.easydo.uc.entity.UserPointsLog;
import plus.easydo.uc.qo.UserPointsQo;
import plus.easydo.uc.service.UserPointsLogService;
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
     * @return plus.easydo.push.server.result.MPDataResult
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
     * @return plus.easydo.push.server.result.MPDataResult
     * @author laoyu
     * @date 2022/7/29
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/userPointsOperation")
    public R<Object> userPointsOperation(@Validated @RequestBody UserPointsOperationDto userPointsOperationDto){
        return userPointsLogService.userPointsOperation(userPointsOperationDto)? DataResult.ok():DataResult.fail();
    }


}
