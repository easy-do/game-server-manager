package plus.easydo.client.model.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description server消息
 * @date 2022/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerMessage {

    private String messageId;

    private int sync;

    private String type;

    private String data;

    public boolean isSync(){
        return sync == 1;
    }


}
