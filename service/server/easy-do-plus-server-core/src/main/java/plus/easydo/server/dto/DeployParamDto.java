package plus.easydo.server.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import plus.easydo.common.vaild.Update;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeployParamDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "应用id不能为空",groups = {Update.class})
    private String applicationId;

    @NotNull(message = "脚本id不能为空",groups = {Update.class})
    private Long appScriptId;

    private JSONObject env;

}
