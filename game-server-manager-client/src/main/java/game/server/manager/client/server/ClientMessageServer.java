package game.server.manager.client.server;

import com.alibaba.fastjson2.JSON;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.common.enums.ClientMessageStatusEnum;
import game.server.manager.common.mode.MessageCallBackData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.ClientMessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/7
 */
@Component
public class ClientMessageServer {

    Logger logger = LoggerFactory.getLogger(ClientMessageServer.class);

    @Autowired
    private SystemUtils systemUtils;

    @Autowired
    private SyncServer syncServer;

    public static final ConcurrentMap<Long, ClientMessageVo> MESSAGE_CACHE = new ConcurrentHashMap<>();

    /**
     * 向本地缓存插入消息
     *
     * @param clientMessageVos clientMessageVos
     * @author laoyu
     * @date 2022/8/7
     */
    public void insertMessage(List<ClientMessageVo> clientMessageVos){
        clientMessageVos.forEach(clientMessageVo -> {
            if(clientMessageVo.getStatus() == ClientMessageStatusEnum.CONSUME.getCode()){
                MESSAGE_CACHE.remove(clientMessageVo.getId());
            }else {
                MESSAGE_CACHE.put(clientMessageVo.getId(),clientMessageVo);
            }
        });
    }

    /**
     * 客户端消息回调
     *
     * @param clientMessageVos clientMessageVos
     * @author laoyu
     * @date 2022/8/7
     */
    public void messageCallBack(List<ClientMessageVo> clientMessageVos){
        List<MessageCallBackData> callBackDataList = clientMessageVos.stream().map(clientMessageVo ->
            MessageCallBackData.builder().clientId(systemUtils.getClientId()).messageId(clientMessageVo.getId()).status(clientMessageVo.getStatus()).messageType(clientMessageVo.getMessageType()).build()
        ).toList();
        R<String> result = syncServer.sync(SyncData.builder().key("clientMessageCallBack").clientId(systemUtils.getClientId()).encryption(true)
                .data(systemUtils.encrypt(JSON.toJSONString(callBackDataList))).build());
        logger.info(result.getMsg());
        insertMessage(clientMessageVos);
    }
}
