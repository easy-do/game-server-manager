package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.server.dto.SysJobDto;
import game.server.manager.server.entity.SysJob;
import game.server.manager.common.enums.JobTypeEnums;
import game.server.manager.common.exception.TaskException;
import game.server.manager.server.job.ScriptJobModel;
import game.server.manager.log.SaveLog;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.server.service.SysJobService;
import game.server.manager.server.job.util.CronUtils;
import game.server.manager.common.vo.SysJobVo;
import game.server.manager.common.vo.UserInfoVo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import java.util.Objects;


/**
 * 调度任务信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/job")
public class SysJobController {
    @Autowired
    private SysJobService jobService;

    /**
     * 查询定时任务列表
     */
    @SaCheckLogin
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<SysJob> wrapper = Wrappers.lambdaQuery();
        if (!AuthorizationUtil.isAdmin()) {
            wrapper.eq(SysJob::getCreateBy, AuthorizationUtil.getUserId());
        }
        IPage<SysJob> page = jobService.page(mpBaseQo.startPage(),wrapper);
        return MpResultUtil.buildPage(page);
    }

    /**
     * 获取定时任务详细信息
     */
    @SaCheckLogin
    @GetMapping(value = "/info/{jobId}")
    public R<SysJobVo> info(@PathVariable("jobId") Long jobId) {
        LambdaQueryWrapper<SysJob> wrapper = Wrappers.lambdaQuery();
        if (!AuthorizationUtil.isAdmin()) {
            wrapper.eq(SysJob::getCreateBy, AuthorizationUtil.getUserId());
        }
        wrapper.eq(SysJob::getJobId, jobId);
        SysJob job = jobService.getOne(wrapper);
        SysJobVo resultVo = BeanUtil.copyProperties(job, SysJobVo.class);
        if(job.getJobType().equals(JobTypeEnums.SCRIPT.getType())){
            ScriptJobModel jobModel = JSON.parseObject(job.getJobParam(), ScriptJobModel.class);
            resultVo.setAppScriptId(jobModel.getAppScriptId());
            resultVo.setServerId(jobModel.getServerId());
            resultVo.setEnv(jobModel.getEnv());
        }
        return DataResult.ok(resultVo);
    }

    /**
     * 新增定时任务
     */
    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "任务管理", description = "添加任务: ?1", expressions = {"#p1.jobName"}, actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysJobDto sysJobDto) throws TaskException, SchedulerException {
        SysJob sysJob = BeanUtil.copyProperties(sysJobDto, SysJob.class);
        if (!CronUtils.isValid(sysJobDto.getCronExpression())) {
            return DataResult.fail("新增任务'" + sysJobDto.getJobName() + "'失败，Cron表达式不正确");
        }
        sysJob.setInvokeTarget(JobTypeEnums.getJobClassByType(sysJobDto.getJobType()));
        if(sysJobDto.getJobType().equals(JobTypeEnums.SCRIPT.getType())){
            ScriptJobModel jobModel = BeanUtil.copyProperties(sysJobDto,ScriptJobModel.class);
            jobModel.setAppScriptId(sysJobDto.getAppScriptId());
            jobModel.setServerId(sysJobDto.getServerId());
            jobModel.setEnv(sysJobDto.getEnv());
            sysJob.setJobParam(JSON.toJSONString(jobModel));
        }
        sysJob.setCreateBy(AuthorizationUtil.getUserId());
        return jobService.insertJob(sysJob) > 0 ? DataResult.ok() : DataResult.fail();
    }

    /**
     * 修改定时任务
     */
    @SaCheckLogin
    @PostMapping("/edit")
    @SaveLog(logType = "操作日志", moduleName = "任务管理", description = "编辑任务: ?1 - ?2", expressions = {"#p1.jobId","#p1.jobName"}, actionType = "编辑")
    public R<Object> edit(@RequestBody @Validated({Update.class}) SysJobDto sysJobDto) throws TaskException, SchedulerException {
        SysJob sysJob = BeanUtil.copyProperties(sysJobDto, SysJob.class);
        if (!CronUtils.isValid(sysJobDto.getCronExpression())) {
            return DataResult.fail("新增任务'" + sysJobDto.getJobName() + "'失败，Cron表达式不正确");
        }
        if(sysJobDto.getJobType().equals(JobTypeEnums.SCRIPT.getType())){
            ScriptJobModel jobModel = BeanUtil.copyProperties(sysJobDto,ScriptJobModel.class);
            jobModel.setAppScriptId(sysJobDto.getAppScriptId());
            jobModel.setServerId(sysJobDto.getServerId());
            jobModel.setEnv(sysJobDto.getEnv());
            sysJob.setJobParam(JSON.toJSONString(jobModel));
        }
        return jobService.updateJob(sysJob) > 0 ? DataResult.ok() : DataResult.fail();
    }

    /**
     * 定时任务状态修改
     */
    @SaCheckLogin
    @PostMapping("/changeStatus")
    public R<Object> changeStatus(@RequestBody SysJob job) throws SchedulerException {
        SysJob newJob = jobService.getById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return jobService.changeStatus(newJob) > 0 ? DataResult.ok() : DataResult.fail();
    }

    /**
     * 定时任务立即执行一次
     */
    @SaCheckLogin
    @GetMapping("/run/{id}")
    @SaveLog(logType = "操作日志", moduleName = "任务管理", description = "执行任务: ?1 ", expressions = {"#p1"}, actionType = "调用")
    public R<Object> run(@PathVariable("id") Long jobId) throws SchedulerException {
        LambdaQueryWrapper<SysJob> wrapper = Wrappers.lambdaQuery();
        if (!AuthorizationUtil.isAdmin()) {
            wrapper.eq(SysJob::getCreateBy, AuthorizationUtil.getUserId());
        }
        wrapper.eq(SysJob::getJobId, jobId);
        SysJob sysJob = jobService.getOne(wrapper);
        if(Objects.nonNull(sysJob)){
            jobService.run(sysJob);
        }
        return DataResult.ok();
    }

    /**
     * 删除定时任务
     */
    @SaCheckLogin
    @GetMapping("/delete/{jobId}")
    @SaveLog(logType = "操作日志", moduleName = "任务管理", description = "删除任务: ?1 ", expressions = {"#p1"}, actionType = "删除")
    public R<Object> remove(@PathVariable Long jobId) throws TaskException, SchedulerException {
        jobService.deleteJobByIds(jobId);
        return DataResult.ok();
    }
}
