package game.server.manager.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.server.entity.SysJobLog;
import game.server.manager.server.service.SysJobLogService;
import game.server.manager.server.mapper.SysJobLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author yuzhanfeng
 * @description 针对表【sys_job_log(定时任务调度日志表)】的数据库操作Service实现
 * @createDate 2022-06-08 17:27:36
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog>
        implements SysJobLogService {
    @Autowired
    private SysJobLogMapper jobLogMapper;


    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId) {
        return jobLogMapper.selectById(jobLogId);
    }

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog) {
        jobLogMapper.insert(jobLog);
    }

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds) {
        return jobLogMapper.deleteBatchIds(Arrays.asList(logIds));
    }

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(Long jobId) {
        return jobLogMapper.deleteById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog() {
        remove(null);
    }
}




