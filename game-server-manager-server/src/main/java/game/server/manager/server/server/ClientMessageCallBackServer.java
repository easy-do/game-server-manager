package game.server.manager.server.server;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import game.server.manager.common.mode.MessageCallBackData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.server.annotation.SyncServerClass;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.ClientMessage;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.service.ClientMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/7
 */
@SyncServerClass("clientMessageCallBack")
public class ClientMessageCallBackServer extends AbstractDefaultServer {

    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private ClientMessageService clientMessageService;

    @Override
    Object processData(SyncData syncData) {
        String clientId = syncData.getClientId();
        String data = syncData.getData();
        Boolean encryption = syncData.getEncryption();
        try {
            if(CharSequenceUtil.isEmpty(clientId)){
                return DataResult.fail("客户端标识为空。");
            }
            ClientInfo dbClientInfo = clientInfoService.getById(clientId);
            if(Objects.isNull(dbClientInfo)){
                return DataResult.fail("客户端不存在。");
            }
            if(encryption){
                RSA rsa = new RSA(dbClientInfo.getPrivateKey(), null);
                data = StrUtil.str(rsa.decrypt(Base64.decode(data), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            }
            JSONArray jsonArray = JSON.parseArray(data);
            List<MessageCallBackData>  messageCallBackDataList = jsonArray.stream().map(json -> JSON.to(MessageCallBackData.class,json)).toList();
            List<ClientMessage> clientMessageList = messageCallBackDataList.stream().map(messageCallBackData -> ClientMessage.builder().id(messageCallBackData.getMessageId()).status(messageCallBackData.getStatus()).build()).toList();
            return clientMessageService.updateBatchById(clientMessageList)?DataResult.okMsg("回调消息状态成功。"):DataResult.fail("回调消息状态失败。");
        } catch (Exception exception) {
            logger.error("接收客户端心跳数据发生异常{}", ExceptionUtil.getMessage(exception));
            return DataResult.fail(ExceptionUtil.getMessage(exception));
        }
    }
}
