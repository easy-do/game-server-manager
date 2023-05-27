package plus.easydo.server.websocket.handler.client;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import plus.easydo.common.constant.MessageTypeConstants;
import plus.easydo.common.enums.ApplicationInstallLogStatusenum;
import plus.easydo.common.mode.InstallLogResultData;
import plus.easydo.common.mode.socket.ClientMessage;
import plus.easydo.redis.RedisUtils;
import plus.easydo.server.entity.ApplicationInstallLog;
import plus.easydo.server.service.ApplicationInstallLogService;
import plus.easydo.server.websocket.handler.AbstractHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

import static plus.easydo.common.constant.SystemConstant.APPLICATION_INSTALL_LOG;

/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 客户端返回的非同步消息处理服务
 */
@Slf4j
@Service(MessageTypeConstants.INSTALL_APPLICATION_RESULT)
public class InstallApplicationResultHandlerService implements AbstractHandlerService<ClientHandlerData> {

    @Autowired
    private ApplicationInstallLogService applicationInstallLogService;

    @Autowired
    private RedisUtils<String> redisUtils;

    @Override
    public Void handler(ClientHandlerData clientHandlerData) {
        ClientMessage clientMessage = clientHandlerData.getClientMessage();
        String messageId = clientMessage.getMessageId();
        String data = clientMessage.getData();
        InstallLogResultData result = JSONUtil.toBean(data, InstallLogResultData.class);
        if(clientHandlerData.getClientMessage().isSuccess() && result.getStatus()){
            String scoreStr = DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_MS_PATTERN);
            double score = Double.parseDouble(scoreStr);
            redisUtils.zAdd(APPLICATION_INSTALL_LOG + messageId , result.getMessage(), score);
            ApplicationInstallLog entity = ApplicationInstallLog.builder()
                    .id(messageId)
                    .status(ApplicationInstallLogStatusenum.INSTALL_ING.getStatus())
                    .build();
            applicationInstallLogService.updateById(entity);
        }else {
            Set<String> logs = redisUtils.zRange(APPLICATION_INSTALL_LOG + messageId, 0, -1);
            logs.add(result.getMessage());
            String logStr = String.join("\n", logs);
            ApplicationInstallLog entity = ApplicationInstallLog.builder()
                    .id(messageId)
                    .logData(logStr)
                    .endTime(LocalDateTime.now())
                    .build();
            if(clientHandlerData.getClientMessage().isSuccess()){
                entity.setStatus(ApplicationInstallLogStatusenum.SUCCESS.getStatus());
            }else {
                entity.setStatus(ApplicationInstallLogStatusenum.FAILED.getStatus());
            }
            applicationInstallLogService.updateById(entity);
            redisUtils.delete(APPLICATION_INSTALL_LOG + messageId);
        }
        return null;
    }
}
