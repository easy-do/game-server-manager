package plus.easydo.server.job.util;

import plus.easydo.server.entity.SysJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author ruoyi
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected Object doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
       return JobInvokeUtil.invokeMethod(sysJob);
    }
}
