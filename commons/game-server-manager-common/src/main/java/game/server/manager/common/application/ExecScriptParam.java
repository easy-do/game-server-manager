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
public class ExecScriptParam {

    private String deviceId;

    private String scriptId;

    private JSONObject env;

    private String executeLogId;

    private String userId;
}
