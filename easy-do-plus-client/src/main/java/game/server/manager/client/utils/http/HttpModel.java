package plus.easydo.push.client.utils.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpModel {

    private String host;

    private String path;

    private Map<String,String> heard;

    private Map<String,String> param;

    private String body;
}


