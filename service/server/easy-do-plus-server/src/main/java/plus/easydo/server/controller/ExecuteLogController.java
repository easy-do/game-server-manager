package plus.easydo.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import plus.easydo.server.entity.ExecuteLog;
import plus.easydo.server.qo.server.ExecuteLogQo;
import plus.easydo.server.service.ExecuteLogService;
import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import plus.easydo.common.vo.ExecuteLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

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
    public MpDataResult page(@RequestBody ExecuteLogQo executeLogQo) {
        LambdaQueryWrapper<ExecuteLog> wrapper = executeLogQo.buildSearchWrapper();
        Object deviceId = executeLogQo.getSearchParam().get("deviceId");
        if (!AuthorizationUtil.isAdmin()) {
            wrapper.eq(ExecuteLog::getCreateBy,AuthorizationUtil.getUserId());
        }
        if(Objects.nonNull(deviceId)){
            wrapper.eq(ExecuteLog::getDeviceId, deviceId);
        }
        wrapper.orderByDesc(ExecuteLog::getCreateTime);
        wrapper.select(ExecuteLog::getId,ExecuteLog::getExecuteState,ExecuteLog::getScriptName,ExecuteLog::getDeviceName,
                ExecuteLog::getStartTime,ExecuteLog::getEndTime,ExecuteLog::getCreateTime);
        return MpResultUtil.buildPage(executeLogService.page(executeLogQo.startPage(), wrapper), ExecuteLogVo.class);
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
