package plus.easydo.server.redis;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSONObject;
import plus.easydo.common.application.ExecScriptParam;
import plus.easydo.event.BasePublishEventServer;
import plus.easydo.redis.RedisStreamUtils;
import plus.easydo.server.application.DeploymentLogServer;
import plus.easydo.server.entity.ExecuteLog;
import plus.easydo.server.service.ExecScriptService;
import plus.easydo.server.service.ExecuteLogService;
import plus.easydo.common.enums.AppStatusEnum;
import plus.easydo.common.vo.LogResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author laoyu
 * @version 1.0
 * @description 执行脚本消息监听
 * @date 2022/7/1
 */
@Slf4j
@Component
public class ExecScriptListenerMessage implements StreamListener<String, MapRecord<String, String, String>> {

    public static final String DEFAULT_GROUP = "applicationDeploy";

    public static final String DEFAULT_NAME = "applicationDeployListener";

    public static final String DEFAULT_STREAM_NAME = "applicationDeployStream";

    @Autowired
    private RedisStreamUtils<Object> redisStreamUtils;

    @Autowired
    private ExecScriptService execScriptService;

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private DeploymentLogServer deploymentLogServer;

     @Autowired
     private BasePublishEventServer basePublishEventServer;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        // 接收到消息
        log.info("消费脚本执行任务 " + message.getId());
        Map<String, String> value = message.getValue();
        AtomicReference<ExecScriptParam> execScriptParam = new AtomicReference<>();

        Callable<String> task = () -> {
            if (value.size() > 1) {
                ExecScriptParam execScriptParamObj = ExecScriptParam.builder()
                        .deviceId(value.get("deviceId").replace("\"",""))
                        .scriptId(value.get("scriptId").replace("\"",""))
                        .userId(value.get("userId").replace("\"",""))
                        .executeLogId(value.get("executeLogId").replace("\"",""))
                        .env(JSONObject.parseObject(value.get("env")))
                        .build();
                execScriptParam.set(execScriptParamObj);
                sendStartMessage(execScriptParamObj);
                return execScriptService.startExecScript(execScriptParam.get());
            }
            return "";
        };
        ScheduledThreadPoolExecutor scheduledExecutor = ThreadUtil.createScheduledExecutor(1);
        Future<String> future = scheduledExecutor.submit(task);
        try {
            //设置超时时间
            future.get(10, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            log.info("消费脚本任务超时{},{} ", message.getId(), message.getValue());
            //获取日志
            Long logId = Long.parseLong(execScriptParam.get().getExecuteLogId());
            LogResultVo logResultVo = deploymentLogServer.getDeploymentLog(logId);
            Collection<String> logs = logResultVo.getLogs();
            logs.add("执行超时,强制结束会话,脚本会继续在后台执行,弹不在记录后续日志。");
            ExecuteLog entity = ExecuteLog.builder().id(logId).executeState(AppStatusEnum.FAILED.getDesc())
                    .logData(String.join("\n",logs)).build();
            deploymentLogServer.cleanDeploymentLog(logId);
            executeLogService.updateById(entity);
        } catch (Exception e) {
            log.info("消费脚本任务失败{},{} ", message.getId(), message.getValue());
            ExecuteLog entity = ExecuteLog.builder().id(Long.parseLong(execScriptParam.get().getExecuteLogId())).executeState(AppStatusEnum.FAILED.getDesc())
                    .logData(ExceptionUtil.getMessage(e)).build();
            executeLogService.updateById(entity);
        } finally {
            scheduledExecutor.shutdown();
            // 确认消费并删除（ACK）
            redisStreamUtils.ack(message.getStream(), DEFAULT_GROUP, message.getId().getValue());
            redisStreamUtils.del(message.getStream(), message.getId().getValue());
        }

    }

    private void sendStartMessage(ExecScriptParam execScriptParamObj) {
        if(Objects.nonNull(execScriptParamObj)){
            basePublishEventServer.publishScriptStartEvent(Long.parseLong(execScriptParamObj.getUserId()), Long.parseLong(execScriptParamObj.getDeviceId()));
        }
    }

}
