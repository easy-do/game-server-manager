package game.server.manager.server.redis;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSONObject;
import game.server.manager.common.application.DeployParam;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.redis.config.RedisStreamUtils;
import game.server.manager.server.application.DeploymentLogServer;
import game.server.manager.server.application.DeploymentServer;
import game.server.manager.server.entity.ExecuteLog;
import game.server.manager.common.enums.AppStatusEnum;
import game.server.manager.server.service.ExecuteLogService;
import game.server.manager.common.vo.DeployLogResultVo;
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
import java.util.stream.Collectors;

/**
 * @author laoyu
 * @version 1.0
 * @description 部署消息监听
 * @date 2022/7/1
 */
@Slf4j
@Component
public class ApplicationDeployListenerMessage implements StreamListener<String, MapRecord<String, String, String>> {

    public static final String DEFAULT_GROUP = "applicationDeploy";

    public static final String DEFAULT_NAME = "applicationDeployListener";

    public static final String DEFAULT_STREAM_NAME = "applicationDeployStream";

    @Autowired
    private RedisStreamUtils<Object> redisStreamUtils;

    @Autowired
    private DeploymentServer deploymentServer;

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
        AtomicReference<DeployParam> deployParam = new AtomicReference<>();

        Callable<String> task = () -> {
            if (value.size() > 1) {
                DeployParam deployParamObj = DeployParam.builder()
                        .applicationId(value.get("applicationId").replace("\"",""))
                        .appScriptId(value.get("appScriptId").replace("\"",""))
                        .userId(value.get("userId").replace("\"",""))
                        .logId(value.get("logId").replace("\"",""))
                        .env(JSONObject.parseObject(value.get("env")))
                        .isClient(Boolean.parseBoolean(value.get("isClient")))
                        .build();
                deployParam.set(deployParamObj);
                sendStartMessage(deployParamObj);
                return deploymentServer.deployment(deployParam.get());
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
            Long logId = Long.parseLong(deployParam.get().getLogId());
            DeployLogResultVo logResultVo = deploymentLogServer.getDeploymentLog(logId);
            Collection<Object> logs = logResultVo.getLogs();
            logs.add("执行超时,强制结束会话,脚本会继续在服务器后台运行,但系统不在记录后续日志。");
            ExecuteLog entity = ExecuteLog.builder().id(logId).executeState(AppStatusEnum.FAILED.getDesc())
                    .logData(logs.stream().map(Object::toString).collect(Collectors.joining())).build();
            deploymentLogServer.cleanDeploymentLog(logId);
            executeLogService.updateById(entity);
        } catch (Exception e) {
            log.info("消费脚本任务失败{},{} ", message.getId(), message.getValue());
            ExecuteLog entity = ExecuteLog.builder().id(Long.parseLong(deployParam.get().getLogId())).executeState(AppStatusEnum.FAILED.getDesc())
                    .logData(ExceptionUtil.getMessage(e)).build();
            executeLogService.updateById(entity);
        } finally {
            scheduledExecutor.shutdown();
            // 确认消费并删除（ACK）
            redisStreamUtils.ack(message.getStream(), DEFAULT_GROUP, message.getId().getValue());
            redisStreamUtils.del(message.getStream(), message.getId().getValue());
        }

    }

    private void sendStartMessage(DeployParam deployParamObj) {
        if(Objects.nonNull(deployParamObj)){
            basePublishEventServer.publishScriptStartEvent(Long.parseLong(deployParamObj.getUserId()), Long.parseLong(deployParamObj.getAppScriptId()));
        }
    }

}
