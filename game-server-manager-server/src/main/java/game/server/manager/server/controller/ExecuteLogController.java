package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.server.entity.ExecuteLog;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.server.service.ExecuteLogService;
import game.server.manager.common.vo.ExecuteLogVo;
import game.server.manager.common.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/3
 */
@RestController
@RequestMapping("/execLog")
public class ExecuteLogController{

    @Autowired
    private ExecuteLogService executeLogService;

    @SaCheckLogin
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        
        LambdaQueryWrapper<ExecuteLog> wrapper = Wrappers.lambdaQuery();
        if (!AuthorizationUtil.isAdmin()) {
            wrapper.eq(ExecuteLog::getCreateBy,AuthorizationUtil.getUserId());
            wrapper.eq(ExecuteLog::getApplicationId, mpBaseQo.getParams().get("applicationId"));
        }else {
            Object applicationId = mpBaseQo.getParams().get("applicationId");
            if(Objects.nonNull(applicationId)){
                wrapper.eq(ExecuteLog::getApplicationId, applicationId);
            }
        }
        wrapper.orderByDesc(ExecuteLog::getCreateTime);
        wrapper.select(ExecuteLog::getId,ExecuteLog::getExecuteState,ExecuteLog::getAppName,ExecuteLog::getScriptName,ExecuteLog::getDeviceName,
                ExecuteLog::getApplicationName,ExecuteLog::getStartTime,ExecuteLog::getEndTime,ExecuteLog::getCreateTime);
        return MpResultUtil.buildPage(executeLogService.page(mpBaseQo.startPage(), wrapper), ExecuteLogVo.class);
    }

    @SaCheckLogin
    @RequestMapping("/info/{id}")
    public R<ExecuteLogVo> info(@PathVariable("id") Long id) {
        
        LambdaQueryWrapper<ExecuteLog> wrapper = Wrappers.lambdaQuery();
        if (!AuthorizationUtil.isAdmin()) {
            wrapper.eq(ExecuteLog::getCreateBy, AuthorizationUtil.getUserId());
        }
        wrapper.eq(ExecuteLog::getId, id);
        ExecuteLogVo executeLogVo = BeanUtil.copyProperties(executeLogService.getOne(wrapper), ExecuteLogVo.class);
        return DataResult.ok(executeLogVo);
    }
}
