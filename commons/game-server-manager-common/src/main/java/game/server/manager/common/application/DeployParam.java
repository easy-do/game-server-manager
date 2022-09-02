package game.server.manager.common.application;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeployParam {

    private String applicationId;

    private String appScriptId;

    private JSONObject env;

    private String logId;

    private String userId;

    private boolean isClient;

    private boolean uninstall;

}
