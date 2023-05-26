package plus.easydo.generate.dynamic.core;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import plus.easydo.common.exception.BizException;
import plus.easydo.generate.dynamic.core.dto.DataSourceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 */
public class HttpDataSourceExecTool {

    private static final Logger logger = LoggerFactory.getLogger(HttpDataSourceExecTool.class);

    private static final String GET = "GET:";

    private static final String POST = "POST:";


    public static JSONObject test(DataSourceDto dataSourceDto){
        String url = dataSourceDto.getUrl();
        String paramsStr = dataSourceDto.getParams();
        JSONObject paramsJson = JSON.parseObject(paramsStr);
        if(url.contains(GET)){
            String result = get(CharSequenceUtil.replace(url, GET, ""), paramsJson);
            try {
                return JSON.parseObject(result);
            }catch (Exception exception){
                throw new BizException("500","测试失败,返回结果非JSON格式");
            }
        }
        if(POST.equals(url)){
            String result = post(CharSequenceUtil.replace(url,POST,""),paramsJson);
            logger.info(result);
            try {
               return JSON.parseObject(result);
            }catch (Exception exception){
                throw new BizException("500","测试失败,返回结果非JSON格式");
            }
        }
        throw new BizException("500","需正确定义http地址,格式< GET:https://xxxxx||POST::https://xxxxx>");
    }


    private static String post(String url , JSONObject paramsJson){
        HttpRequest httpRequest = HttpRequest.post(url);
        putParams(httpRequest,paramsJson);
        return exec(httpRequest);
    }

    private static String get(String url , JSONObject paramsJson){
        HttpRequest httpRequest = HttpRequest.get(url);
        putParams(httpRequest,paramsJson);
        return exec(httpRequest);
    }

    private static String exec(HttpRequest httpRequest){
        try {
            return httpRequest.timeout(2000).execute().body();
        }catch (Exception exception){
            throw new BizException("500",exception.getMessage());
        }
    }

    private static HttpRequest putParams(HttpRequest httpRequest, JSONObject paramsJson){
        if(Objects.nonNull(paramsJson)){
            String headers = paramsJson.getString("headers");
            JSONObject values = paramsJson.getJSONObject("values");
            if(CharSequenceUtil.isNotBlank(headers)){
                httpRequest.headerMap((Map<String, String>) JSON.parse(headers),true);
            }
            if(Objects.nonNull(values)){
                httpRequest.form(values);
            }
        }
        return httpRequest;
    }

}
