package plus.easydo.common.mode;

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
public class PluginsInitData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String applicationId;

    private String applicationName;

    private Long appId;

    private String appName;

    private String publicKey;

    private String startCmd;

    private String stopCmd;

    private String configFilePath;

}
