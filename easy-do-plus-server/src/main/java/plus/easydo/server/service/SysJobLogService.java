package plus.easydo.server.service;

import plus.easydo.server.entity.SysJobLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yuzhanfeng
* @description 针对表【sys_job_log(定时任务调度日志表)】的数据库操作Service
* @createDate 2022-06-08 17:27:36
*/
public interface SysJobLogService extends IService<SysJobLog> {

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    public SysJobLog selectJobLogById(Long jobLogId);

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     */
    public void addJobLog(SysJobLog jobLog);

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的日志ID
     * @return 结果
     */
    public int deleteJobLogByIds(Long[] logIds);

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     * @return 结果
     */
    public int deleteJobLogById(Long jobId);

    /**
     * 清空任务日志
     */
    public void cleanJobLog();
}
