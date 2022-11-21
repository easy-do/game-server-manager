package game.server.manager.client.server;

import com.alibaba.fastjson2.JSON;
import game.server.manager.client.websocket.ClientWebsocketEndpoint;
import game.server.manager.common.constant.PathConstants;
import game.server.manager.common.mode.SyncData;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.common.result.R;
import game.server.manager.common.utils.http.HttpModel;
import game.server.manager.common.utils.http.HttpRequestUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 */
@Data
@Component
public class SyncServer {

    @Autowired
    private SystemUtils systemUtils;

    @Autowired
    private ClientDataServer clientDataServer;

    private ClientWebsocketEndpoint client;

    public R<String> sync(SyncData syncData) {
        //TODO 使用socket连接 待实现


        String resultStr = HttpRequestUtil.post(
                HttpModel.builder()
                        .host(systemUtils.getManagerUrl())
                        .path(PathConstants.SYNC)
                        .body(JSON.toJSONString(syncData)).build());
        return HttpRequestUtil.unPackage(resultStr);
    }

    public void createClient() {
        URI uri = null;
        try {
            uri = new URI(systemUtils.getServerSocketUrl());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.client = new ClientWebsocketEndpoint(uri,systemUtils.getClientId());
        client.connect();
    }

}
