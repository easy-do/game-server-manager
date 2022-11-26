package game.server.manager.server.handler;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.mode.ClientData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.handler.AbstractHandlerService;
import game.server.manager.handler.annotation.HandlerService;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.service.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 */
@HandlerService("clientData")
public class ClientDataHandlerService extends AbstractHandlerService<SyncData,Object> {

    @Autowired
    private ClientInfoService clientInfoService;

    @Override
    public Object handler(SyncData syncData) {
        String clientId = syncData.getClientId();
        String data = syncData.getData();
        Boolean encryption = syncData.getEncryption();
        try{
            ClientInfo dbClientInfo = clientInfoService.getById(clientId);
            if (Objects.isNull(dbClientInfo)) {
                return DataResult.fail("客户端不存在");
            }
            if(encryption){
               RSA rsa = new RSA(dbClientInfo.getPrivateKey(), null);
               data = StrUtil.str(rsa.decrypt(Base64.decode(data), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            }
            ClientData clientData = JSON.parseObject(data, ClientData.class);
            if (CharSequenceUtil.isEmpty(clientData.getClientId())) {
                return DataResult.fail("客户端标识为空");
            }
            if (CharSequenceUtil.isEmpty(clientData.getVersion())) {
                return DataResult.fail("未知的版本信息");
            }
            if (CharSequenceUtil.isEmpty(clientData.getIp())) {
                return DataResult.fail("未知的服务器ip");
            }
            ClientInfo clientInfo = ClientInfo.builder()
                    .id(clientId)
                    .lastUpTime(LocalDateTime.now())
                    .clientData(JSON.toJSONString(clientData))
                    .build();
                return clientInfoService.updateById(clientInfo)?DataResult.ok():DataResult.fail();
        }catch (Exception exception){
            logger.error("接收客户端启动数据发生异常{}", ExceptionUtil.getMessage(exception));
            return DataResult.fail("同步客户端数据发生错误");
        }
    }
}
