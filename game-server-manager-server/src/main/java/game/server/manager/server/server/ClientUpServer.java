package game.server.manager.server.server;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.enums.AppStatusEnum;
import game.server.manager.common.mode.ClientData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.vo.ClientMessageVo;
import game.server.manager.server.annotation.SyncServerClass;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.ClientMessage;
import game.server.manager.server.service.ClientInfoService;
import game.server.manager.server.service.ClientMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 */
@SyncServerClass("clientUp")
public class ClientUpServer extends AbstractDefaultServer {

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
            ClientData clientData = JSON.parseObject(data, ClientData.class);
            if (CharSequenceUtil.isEmpty(clientData.getIp())) {
                return DataResult.fail("未知的服务器ip");
            }
            if (CharSequenceUtil.isEmpty(clientData.getVersion())) {
                return DataResult.fail("未知的版本信息");
            }
            ClientInfo clientInfo = ClientInfo.builder()
                    .id(clientId)
                    .clientData(JSON.toJSONString(clientData))
                    .lastUpTime(LocalDateTime.now())
                    .status(AppStatusEnum.DEPLOYMENT_SUCCESS.getDesc()).build();
            boolean result = clientInfoService.updateById(clientInfo);
            if(result){
                //查询客户端需要消费的消息
                List<ClientMessageVo> messageVos = clientMessageService.getNoConsumeMessage(clientId);
                return DataResult.ok(messageVos,"确认存活");
            }
            return DataResult.fail("更新存活状态失败。");
        } catch (Exception exception) {
            logger.error("接收客户端心跳数据发生异常{}", ExceptionUtil.getMessage(exception));
            return DataResult.fail(ExceptionUtil.getMessage(exception));
        }
    }


}
