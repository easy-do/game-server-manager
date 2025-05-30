package plus.easydo.server.application;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import plus.easydo.redis.RedisUtils;
import plus.easydo.server.entity.ExecuteLog;
import plus.easydo.server.service.ExecuteLogService;
import plus.easydo.common.enums.AppStatusEnum;
import plus.easydo.common.vo.LogResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */

@Component
public class DeploymentLogServer {

    private static final String PREFIX = "easy_push_oauth:deployment_log:";

    @Autowired
    private RedisUtils<String> redisUtils;

    @Autowired
    private ExecuteLogService executeLogService;


    public void saveDeploymentLog(Long logId,String log){
        String scoreStr = DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_MS_PATTERN);
        double score = Double.parseDouble(scoreStr);
        redisUtils.zAdd(PREFIX + logId , log, score);
    }

    public LogResultVo getDeploymentLog(Serializable logId) {
        ExecuteLog executeLog = executeLogService.getById(logId);
        if(Objects.isNull(executeLog)){
            return LogResultVo.builder().isFinish(true).logs(List.of("日志不存在")).build();
        }
        String state = executeLog.getExecuteState();
        //如果进行中则从缓存读取
        Collection<String> logs;
        if (state.equals(AppStatusEnum.STARTING.getDesc())) {
            logs = redisUtils.zRange(PREFIX + logId, 0, -1);
            return LogResultVo.builder().isFinish(false).logs(logs).build();
        } else {
            //如果已经执行完成直接返回数据库的日志
            String log = executeLog.getLogData();
            if(CharSequenceUtil.isNotEmpty(log)){
                logs = List.of(log);
            }else {
                logs = Collections.emptyList();
            }
            return LogResultVo.builder().isFinish(true).logs(logs).build();
        }
    }

    public void cleanDeploymentLog(Long logId){
        redisUtils.delete(PREFIX + logId);
    }



}
