package game.server.manager.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {

    private Long userId;

    private String title;

    private String name;

    private String status;

    private String description;
}
