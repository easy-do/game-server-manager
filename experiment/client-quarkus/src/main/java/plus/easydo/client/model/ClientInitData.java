package plus.easydo.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInitData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String clientId;

    private String clientName;

    private String publicKey;

    private String sessionId;

}
