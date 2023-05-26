package plus.easydo.client.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


/**
 * @author laoyu
 * @version 1.0
 * @description ip属地查询
 * @date 2022/7/28
 */
public class IpRegionSearchUtil {

    private static final Logger log = LoggerFactory.getLogger(IpRegionSearchUtil.class);

    private static Searcher searcher;

    public static String ip;

    static {
        String moduleName = "plus.easydo.client";
        String resourcePath = "/ip.xdb";
        byte[] cBuff = null;
        try {
            log.info("初始化加载ip2region数据文件");
            Module resource = ModuleLayer.boot().findModule(moduleName).get();
            InputStream ins = resource.getResourceAsStream(resourcePath);
            cBuff = IoUtil.readBytes(ins);
        } catch (IOException e) {
            log.error("初始化加载ip2region数据文件失败, {}", e.getMessage());
        }
        try {
            log.info("初始化 ip2region searcher");
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (IOException e) {
            log.error("初始化 ip2region searcher 失败, {}", e.getMessage());
        }
    }


    private IpRegionSearchUtil() {
    }

    public static String searchRequestIp(String ip) {
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((System.nanoTime() - sTime));
            log.info("{ip:{}, region: {}, ioCount: {}, took: {}}", ip, region, searcher.getIOCount(), cost);
            return region;
        } catch (Exception e) {
            log.error("ip2region查询异常：{}", e.getMessage());
            return "";
        }
    }

//    public static String searchRequestIp() {
//        String ip = IpUtil.getIpAddress();
//        return search(ip);
//    }

    public static String searchServerIp() {
        if(CharSequenceUtil.isEmpty(ip)){
            ip = MyWebIpUtil.getnowIp();
        }
        return search(ip);
    }

    public static String search(String ip) {
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((System.nanoTime() - sTime));
            log.info("{ip:{}, region: {}, ioCount: {}, took: {}}", ip, region, searcher.getIOCount(), cost);
            return ip + " |" + region;
        } catch (Exception e) {
            log.error("ip2region查询异常：{}", e.getMessage());
            return ip;
        }
    }

}
