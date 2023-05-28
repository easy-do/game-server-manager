package plus.easydo.client.model.socket;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description docker socket接口通信数据封装
 * @date 2022/11/21
 */
@Data
public class BrowserMessage {

    private String token;

    private String type;

    private String data;
}
