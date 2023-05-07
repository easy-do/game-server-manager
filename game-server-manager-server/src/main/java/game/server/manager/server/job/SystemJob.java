package game.server.manager.server.job;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.StrBuilder;
import game.server.manager.common.enums.ApplicationInstallLogStatusenum;
import game.server.manager.server.entity.ApplicationInstallLog;
import game.server.manager.server.service.ApplicationInstallLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 * @description 系统任务
 * @date 2022/11/21
 */
@Slf4j
@Component
public class SystemJob {

    @Autowired
    private ApplicationInstallLogService applicationInstallLogService;

    @Scheduled(fixedDelay = 1000 * 30)
    public void HeartbeatCheck() {
        log.info("计算是否存在超时超时安装任务-start");
        List<ApplicationInstallLog> list = applicationInstallLogService.getNoFinishLog();
        List<ApplicationInstallLog> updateList = new ArrayList<>();
        list.forEach(log->{
            Duration duration = LocalDateTimeUtil.between(log.getUpdateTime(), LocalDateTime.now());
            if(duration.toMillis() > 6000L){
                log.setStatus(ApplicationInstallLogStatusenum.FAILED.getStatus());
                log.setLogData(StrBuilder.create(log.getLogData()).append("exec timeout!").toString());
                updateList.add(log);
            }
        });
        log.info("计算是否存在超时超时安装任务-end,共{}条记录",updateList.size());
        applicationInstallLogService.updateBatchById(updateList);
    }
}
