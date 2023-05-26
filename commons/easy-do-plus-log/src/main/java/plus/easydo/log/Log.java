package plus.easydo.log;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description log对象
 * @date 2022/6/18
 */
@Slf4j
@Data
@Builder
public class Log {

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 源地址，发起人ip
     */
    private String sourceIp;
    /**
     * 目标地址，服务ip
     */
    private String targetIp;
    /**
     * 请求地址
     */
    private String path;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 操作时间（必填，格式为 yyyy-MM-dd HH:mm:ss）
     */
    private String operationTime;
    /**
     * 操作人id
     */
    private String operatorId;
    /**
     * 操作人名称
     */
    private String operatorName;
    /**
     * 客户端类型：PC端、移动端
     */
    private String clientType;
    /**
     * 模块/菜单名称
     */
    private String moduleName;
    /**
     * 操作类型（自定义）
     */
    private String actionType;
    /**
     * 操作内容（必填）
     */
    private String description;
    /**
     * 操作状态。成功、失败（必填）
     */
    private String operationStatus;
    /**
     * 错误信息
     */
    private String errMessage;
    /**
     * 其他扩展信息
     */
    private Map<String, String> extMap;


    /**
     * 设置扩展属性
     *
     * @param key
     * @param value
     * @return
     */
    public void putExtMap(String key, String value) {
        if (this.extMap == null) {
            this.extMap = new HashMap<>(16);
        }

        this.extMap.put(key, value);
    }
}
