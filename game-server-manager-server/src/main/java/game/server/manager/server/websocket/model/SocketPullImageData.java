package game.server.manager.server.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/11/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketPullImageData {

    private Session session;

    private Long userId;

    private Boolean isAdmin;

    private String dockerId;

    private String repository;
}
