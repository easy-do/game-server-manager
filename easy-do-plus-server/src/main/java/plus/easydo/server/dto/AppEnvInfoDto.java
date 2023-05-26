package plus.easydo.server.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 脚本变量
 * @author yuzhanfeng
 */
@Data
public class AppEnvInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Long id;

    /**
     * 所属脚本id
     */
    private Long appId;

    /**
     * 变量名称
     */
    private String envName;

    /**
     * 变量key
     */
    private String envKey;

    /**
     * shell脚本取参key
     */
    private String shellKey;

    /**
     * 变量参数-默认值
     */
    private String envValue;

    /**
     * 变量类型
     */
    private String envType;

    /**
     * 描述
     */
    private String description;

}
