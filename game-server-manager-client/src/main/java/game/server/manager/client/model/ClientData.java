package game.server.manager.client.model;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 应用信息
 *
 * @author yuzhanfeng
 */
@Data
public class ClientData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    private String clientId;

    /**
     * 环境ip地址
     */
    private String ip;

    /**
     * 功能描述
     *
     * @param null null
     * @return
     * @author laoyu
     * @date 2022/11/29
     */
    private String port;

    /**
     * 客户端版本
     */
    private String version;

    /**
     * 环境变量
     */
    private JSONObject env;

    /**
     * 系统信息
     */
    private SystemInfo systemInfo;

}
