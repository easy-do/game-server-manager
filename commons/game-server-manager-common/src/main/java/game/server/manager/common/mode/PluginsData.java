package game.server.manager.common.mode;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 插件信息
 *
 * @author yuzhanfeng
 */
@Data
public class PluginsData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    private String applicationId;

    /**
     * 环境ip地址
     */
    private String ip;

    /**
     * 应用版本
     */
    private String version;

    /**
     * 环境变量
     */
    private String env;

    /**
     * 配置文件
     */
    private String config;

    /**
     * 系统信息
     */
    private SystemInfo systemInfo;

}