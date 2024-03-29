package plus.easydo.common.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author yuzhanfeng
 * @TableName app_script
 */
@Data
public class ScriptDataVo implements Serializable {

    private Long id;

    /**
     * 脚本名称
     */
    private String scriptName;

    /**
     * 脚本类型
     */
    private String scriptType;

    /**
     * 依赖脚本
     */
    private String basicScript;

    /**
     * 作用范围
     *
     */
    private String scriptScope;

    /**
     * 版本
     */
    private String version;

    /**
     * 环境变量
     */
    private List<ScriptEnvDataVo> scriptEnv;

    /**
     * 脚本文件
     */
    private String scriptFile;

    /**
     * 介绍
     */
    private String description;

    /**
     * 热度
     */
    private Long heat;

    /**
     * 作者
     */
    private String author;


    @Serial
    private static final long serialVersionUID = 1L;
}
