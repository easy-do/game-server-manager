package game.server.manager.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuzhanfeng
 * @Date 2022/8/16 16:30
 * @Description 客户端卸载事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientUninstallEvent {

    private String clientId;

}
