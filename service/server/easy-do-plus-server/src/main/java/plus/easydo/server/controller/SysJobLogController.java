package plus.easydo.server.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.server.entity.SysJobLog;
import plus.easydo.server.service.SysJobLogService;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

import java.util.Collections;
import java.util.List;


/**
 * 调度日志操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/job/log")
public class SysJobLogController {
    @Autowired
    private SysJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/page")
    public MpDataResult list(@RequestBody MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<SysJobLog> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysJobLog::getJobLogId,SysJobLog::getJobName,SysJobLog::getJobId,SysJobLog::getJobMessage,SysJobLog::getCreateTime,SysJobLog::getStatus);
        IPage<SysJobLog> page = jobLogService.page(mpBaseQo.startPage(),wrapper);
        return MpResultUtil.buildPage(page);
    }

    /**
     * 根据调度编号获取详细信息
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/info/{jobLogId}")
    public R<Object> getInfo(@PathVariable Long jobLogId) {
        return DataResult.ok(jobLogService.selectJobLogById(jobLogId));
    }

    /**
     * 根据id获取加班执行结果的行集合
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/logResult/{jobLogId}")
    public R<List<String>> logResult(@PathVariable Long jobLogId) {
        SysJobLog sysJobLog = jobLogService.selectJobLogById(jobLogId);
        String result = sysJobLog.getResult();
        if(CharSequenceUtil.isNotEmpty(result)){
            return DataResult.ok(CharSequenceUtil.split(result,"\n"));
        }
        return DataResult.ok(Collections.emptyList());
    }

    /**
     * 根据任务id获取日志列表
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping(value = "/listByJobId/{jobId}")
    public R<List<SysJobLog>> listByJobId(@PathVariable Long jobId) {
        LambdaQueryWrapper<SysJobLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysJobLog::getJobId,jobId);
        wrapper.select(SysJobLog::getJobLogId,SysJobLog::getJobId,SysJobLog::getCreateTime,SysJobLog::getStatus);
        List<SysJobLog> list = jobLogService.list(wrapper);
        return DataResult.ok(list);
    }

    /**
     * 删除定时任务调度日志
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @GetMapping("/delete/{jobLogIds}")
    public R<Object> remove(@PathVariable Long[] jobLogIds) {
        return jobLogService.deleteJobLogByIds(jobLogIds) > 0 ? DataResult.ok() : DataResult.fail();
    }

    /**
     * 清空定时任务调度日志
     */
    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @DeleteMapping("/clean")
    public R<Object> clean() {
        jobLogService.cleanJobLog();
        return DataResult.ok();
    }
}
