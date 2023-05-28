package plus.easydo.client.utils;

import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/4
 */
public class MyWebIpUtil {
    private static final Pattern PATTERN = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");

    public static String getnowIp(){
        String ip = null;
        try {
            ip = getNowIp1();
            if(Objects.nonNull(ip)){
                return ip;
            }
        } catch (Exception e) {
            try {
                ip = getNowIp2();
                if(Objects.nonNull(ip)){
                    return ip;
                }
            } catch (Exception ex) {
                try {
                    ip = getNowIp3();
                    if(Objects.nonNull(ip)){
                        return ip;
                    }
                } catch (Exception exc) {
                    try {
                        ip = getNowIp4();
                        if(Objects.nonNull(ip)){
                            return ip;
                        }
                    } catch (Exception ioException) {
                        return "";
                    }
                }

            }
        }
        return "";
    }

    public static String getNowIp1() throws IOException {
        String ip = null;
        String china = "http://ip.chinaz.com";
        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(china);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
            Matcher m = PATTERN.matcher(inputLine.toString());
            if (m.find()) {
                String ipstr = m.group(1);
                ip = ipstr;
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        if (StrUtil.isEmpty(ip)) {
            throw new RuntimeException();
        }
        return ip;
    }

    public static String getNowIp2() throws IOException {
        String ip = null;
        BufferedReader br = null;
        try {
            URL url = new URL("https://v6r.ipip.net/?format=callback");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = "";
            StringBuffer sb = new StringBuffer("");
            String webContent = "";
            while ((s = br.readLine()) != null) {
                sb.append(s + "\r\n");
            }
            webContent = sb.toString();
            int start = webContent.indexOf("(") + 2;
            int end = webContent.indexOf(")") - 1;
            webContent = webContent.substring(start, end);
            ip = webContent;
        } finally {
            if (br != null) {
                br.close();
            }
        }
        if (StrUtil.isEmpty(ip)) {
            throw new RuntimeException();
        }
        return ip;
    }


    public static String getNowIp3() throws IOException {
        String ip = null;
        String objWebUrl = "https://ip.900cha.com/";
        BufferedReader br = null;
        try {
            URL url = new URL(objWebUrl);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = "";
            String webContent = "";
            while ((s = br.readLine()) != null) {
                if (s.indexOf("我的IP:") != -1) {
                    ip = s.substring(s.indexOf(":") + 1);
                    break;
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        if (StrUtil.isEmpty(ip)) {
            throw new RuntimeException();
        }
        return ip;
    }
    public static String getNowIp4() throws IOException {
        String ip = null;
        String objWebUrl = "https://bajiu.cn/ip/";
        BufferedReader br = null;
        try {
            URL url = new URL(objWebUrl);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = "";
            while ((s = br.readLine()) != null) {
                if (s.contains("互联网IP")) {
                    ip = s.substring(s.indexOf("'") + 1, s.lastIndexOf("'"));
                    break;
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        if (StrUtil.isEmpty(ip)) {
            throw new RuntimeException();
        }
        return ip;
    }
}
