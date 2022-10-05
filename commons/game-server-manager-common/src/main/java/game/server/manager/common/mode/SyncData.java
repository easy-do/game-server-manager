package game.server.manager.common.mode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String sessionId;
    private String applicationId;
    private String clientId;
    private String key;
    private String data;
    private Boolean encryption;
}
