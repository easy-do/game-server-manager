package plus.easydo.generate.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description TODO
 * @date 2023/5/16
 */

public class Test1 {

    public static void main(String[] args) throws IOException {
        String vm = "@startuml\n" +
                "!theme plain\n" +
                "usecase UC2\n" +
                ":UC2: -l-> (dummyLeft)\n" +
                ":UC2: -r-> (dummyRight)\n" +
                ":UC2: -u-> (dummyUp)\n" +
                ":UC2: -d-> (dummyDown)\n" +
                ":UC2: -l-> (dummyLeft1)\n" +
                ":UC2: -r-> (dummyRight1)\n" +
                ":UC2: -u-> (dummyUp1)\n" +
                ":UC2: -d-> (dummyDown1)\n" +
                ":UC2: -l-> (dummyLeft2)\n" +
                ":UC2: -r-> (dummyRight2)\n" +
                ":UC2: -u-> (dummyUp2)\n" +
                ":UC2: -d-> (dummyDown2)\n" +
                ":UC2: -l-> (dummyLeft3)\n" +
                ":UC2: -r-> (dummyRight3)\n" +
                ":UC2: -u-> (dummyUp3)\n" +
                ":UC2: -d-> (dummyDown3)\n" +
                ":UC2: -l-> (dummyLeft4)\n" +
                ":UC2: -r-> (dummyRight4)\n" +
                ":UC2: -u-> (dummyUp4)\n" +
                ":UC2: -d-> (dummyDown4)\n" +
                "@enduml";
        Map<String, String> formMap = new HashMap<>();
        formMap.put("text",vm);
        HttpRequest request = HttpRequest.post("http://192.168.123.129:8282/form").formStr(formMap);
        CookieManager cookieManager = HttpRequest.getCookieManager();
        HttpResponse res = request.execute();
        String location = res.header("Location");
        String pngUrl = "http://192.168.123.129:8282/png/"+ location.replace("/uml/","");
        InputStream inputStream = HttpRequest.get(pngUrl).execute().bodyStream();
        File file = FileUtil.file("E:/" + UUID.fastUUID().toString(true) + ".png");
        IoUtil.copy(inputStream,FileUtil.getOutputStream(file));
        inputStream.close();

    }

}
