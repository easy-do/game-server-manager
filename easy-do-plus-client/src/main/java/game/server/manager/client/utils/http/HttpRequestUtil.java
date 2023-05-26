package plus.easydo.push.client.utils.http;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import plus.easydo.push.client.result.R;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/30
 */

public class HttpRequestUtil {


    public static String get(HttpModel httpModel) {
        Map<String, String> header = httpModel.getHeard();
        if(Objects.isNull(header)){
            header = MapUtil.newConcurrentHashMap(2);
            header.put("Content-Type", "application/json;charset=utf-8");
        }
        String buildUrl = buildUrl(httpModel);
        return HttpRequest.get(buildUrl).headerMap(header, true).execute().body();
    }


    public static String post(HttpModel httpModel) {
        Map<String, String> header = httpModel.getHeard();
        if(Objects.isNull(header)){
            header = MapUtil.newConcurrentHashMap(2);
            header.put("Content-Type", "application/json;charset=utf-8");
        }
        String buildUrl = buildUrl(httpModel);
        return HttpRequest.post(buildUrl).body(httpModel.getBody()).headerMap(header, true).execute().body();
    }



    public static String buildUrl(HttpModel httpModel) {
        Map<String, String> header = httpModel.getHeard();
        if(Objects.isNull(header)){
            header = MapUtil.newConcurrentHashMap(2);
            header.put("Content-Type", "application/json;charset=utf-8");
        }
        return URLUtil.decode(buildUrl(httpModel.getHost(), httpModel.getPath(), httpModel.getParam()));
    }

    public static String buildUrl(String host, String path, Map<String, String> param) {
        StringBuilder stringBuffer = new StringBuilder();
        String queryUrl = new UrlQuery(param).build(StandardCharsets.UTF_8);
        stringBuffer.append(host).append(path);
        if(!path.contains("?")){
            stringBuffer.append("?");
        }else {
            stringBuffer.append("&");
        }
        stringBuffer.append(queryUrl);
        return stringBuffer.toString();
    }


    public static R<String> unPackage(String resultStr){
        ObjectMapper mapper = new ObjectMapper();
        R<String> r = null;
        try {
            r = mapper.readValue(resultStr, R.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(!r.isSuccess()){
            throw new RuntimeException(r.getErrorMessage());
        }
        return r;
    }

}
