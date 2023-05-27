package plus.easydo.generate.dto;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 */
@Data
public class JsonGenerateDto {

    /**
     * json内容
     */
    private JSONArray contentArray;

    /**
     * json内容
     */
    private JSONObject contentJson;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 生成文件名取值字段
     */
    private String fileNameField;
}
