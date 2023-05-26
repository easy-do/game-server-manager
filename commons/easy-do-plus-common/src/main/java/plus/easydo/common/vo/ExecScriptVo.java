package plus.easydo.common.vo;

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
public class ExecScriptVo {

    private String deviceId;

    private Integer deviceType;

    private String scriptId;

    private JSONObject env;

    private String executeLogId;

}
