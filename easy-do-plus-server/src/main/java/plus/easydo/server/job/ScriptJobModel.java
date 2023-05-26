package plus.easydo.server.job;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/6/9
 */
@Data
public class ScriptJobModel {

    /**
     * 脚本id
     */
    private Long appScriptId;

    /**
     * 服务器id
     */
    private Long serverId;

    /**
     * 脚本参数
     */
    private JSONObject env;
}
