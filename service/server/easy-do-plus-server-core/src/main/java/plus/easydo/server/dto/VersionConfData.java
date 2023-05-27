package plus.easydo.server.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 版本配置信息
 * @date 2023/3/19
 */
@NoArgsConstructor
@Data
public class VersionConfData {

    private String image;

    private JSONObject envs;
}
