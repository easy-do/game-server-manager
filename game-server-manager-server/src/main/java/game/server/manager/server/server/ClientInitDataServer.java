package game.server.manager.server.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.mode.ClientInitData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.server.annotation.SyncServerClass;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.service.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/31
 */
@SyncServerClass("clientInitData")
public class ClientInitDataServer extends AbstractDefaultServer {

    @Autowired
    private ClientInfoService clientInfoService;

    @Override
    Object processData(SyncData syncData) {
        String clientId = syncData.getClientId();
        try{
            if(CharSequenceUtil.isEmpty(clientId)){
                return DataResult.fail("客户端标识为空。");
            }
            ClientInfo clientInfo = clientInfoService.getById(clientId);
            if(Objects.isNull(clientInfo)){
                return DataResult.fail("客户端不存在。");
            }
            ClientInitData initData = ClientInitData.builder()
                    .clientId(clientInfo.getId())
                    .clientName(clientInfo.getClientName())
                    .publicKey(clientInfo.getPublicKey())
                    .build();
            return DataResult.ok(initData);
        }catch (Exception exception){
            logger.error("接收客户端启动数据发生异常{}",JSON.toJSONString(syncData));
            return DataResult.fail("获取初始化数据失败，{}", ExceptionUtil.getMessage(exception));
        }


    }
}
