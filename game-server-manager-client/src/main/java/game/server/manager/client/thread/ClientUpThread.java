package game.server.manager.client.thread;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.client.server.ClientMessageServer;
import game.server.manager.common.enums.ClientMessageStatusEnum;
import game.server.manager.common.mode.ClientData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.server.ClientDataServer;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.ClientMessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 */
@Component
public class ClientUpThread {

    Logger logger = LoggerFactory.getLogger(ClientUpThread.class);
    @Autowired
    private ClientDataServer clientDataServer;

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private SystemUtils systemUtils;

    @Autowired
    private ClientMessageServer clientMessageServer;

//    @Scheduled(fixedDelay = 1000 * 10)
    public void serverUp() {
        ClientData clientData = clientDataServer.getClientData();
        try {
            String data = JSON.toJSONString(clientData);
            R<String> result = syncServer.sync(SyncData.builder().key("clientUp").clientId(clientData.getClientId()).encryption(true).data(systemUtils.encrypt(data)).build());
            logger.info("up, {}", result.getMsg());
            String resultData = result.getData();
            JSONArray jsonArray = JSONArray.parseArray(resultData);
            if(!jsonArray.isEmpty()){
                logger.info("接受到{}条待消费消息。", jsonArray.size());
                List<ClientMessageVo> messageVos = jsonArray.stream().map(json -> {
                    ClientMessageVo clientMessageVo = JSON.to(ClientMessageVo.class, json);
                    clientMessageVo.setStatus(ClientMessageStatusEnum.ACCEPT.getCode());
                    return clientMessageVo;
                }).toList();
                clientMessageServer.messageCallBack(messageVos);
            }
        } catch (Exception exception) {
            logger.error("心跳通信失败, {}",ExceptionUtil.getMessage(exception));
        }
    }
}
