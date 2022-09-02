package game.server.manager.server.service;

import game.server.manager.server.entity.SysJob;
import com.baomidou.mybatisplus.extension.service.IService;
import game.server.manager.common.exception.TaskException;
import org.quartz.SchedulerException;

/**
* @author yuzhanfeng
* @description 针对表【sys_job(定时任务调度表)】的数据库操作Service
* @createDate 2022-06-08 17:27:36
*/
public interface SysJobService extends IService<SysJob> {


    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    public SysJob selectJobById(Long jobId);

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     * @throws SchedulerException e
     */
    public int pauseJob(SysJob job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     * @throws SchedulerException e
     */
    public int resumeJob(SysJob job) throws SchedulerException;

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     * @return 结果
     * @throws SchedulerException e
     */
    public int deleteJob(SysJob job) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param jobId 需要删除的任务ID
     * @throws SchedulerException e
     */
    public void deleteJobByIds(Long jobId) throws SchedulerException;

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     * @return 结果
     * @throws SchedulerException e
     */
    public int changeStatus(SysJob job) throws SchedulerException;

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     * @return 结果
     * @throws SchedulerException e
     */
    public void run(SysJob job) throws SchedulerException;

    /**
     * 新增任务
     *
     * @param job 调度信息
     * @return 结果
     * @throws SchedulerException e
     * @throws TaskException e
     */
    public int insertJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 更新任务
     *
     * @param job 调度信息
     * @return 结果
     * @throws SchedulerException e
     * @throws TaskException e
     */
    public int updateJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    public boolean checkCronExpressionIsValid(String cronExpression);

}
