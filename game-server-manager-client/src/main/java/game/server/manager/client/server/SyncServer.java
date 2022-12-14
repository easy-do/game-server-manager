package game.server.manager.client.server;

import com.alibaba.fastjson2.JSON;
import game.server.manager.common.constant.PathConstants;
import game.server.manager.common.mode.SyncData;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.common.result.R;
import game.server.manager.common.utils.http.HttpModel;
import game.server.manager.common.utils.http.HttpRequestUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

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

    private Socket socket;

    public R<String> sync(SyncData syncData) {
        //TODO 使用socket连接 待实现


        String resultStr = HttpRequestUtil.post(
                HttpModel.builder()
                        .host(systemUtils.getManagerUrl())
                        .path(PathConstants.SYNC)
                        .body(JSON.toJSONString(syncData)).build());
        return HttpRequestUtil.unPackage(resultStr);
    }


}
