package plus.easydo.server.dto;

import lombok.Data;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author yuzhanfeng
 * @TableName script_data
 */
@Data
public class ScriptDataDto implements Serializable {

    @NotNull(message = "id不能为空",groups = {Update.class})
    private Long id;

    /**
     * 脚本名称
     */
    @Size(min = 2,max = 12,message = "脚本名称长度2-12")
    @NotNull(message = "脚本名称不能为空",groups = {Insert.class,Update.class})
    private String scriptName;

    /**
     * 脚本类型
     */
    @NotNull(message = "脚本类型不能为空",groups = {Insert.class,Update.class})
    private String scriptType;

    /**
     * 依赖脚本
     */
    private String basicScript;

    /**
     * 可见范围
     *
     */
    private String scriptScope;

    /**
     * 版本
     */
    @Size(min = 2,max = 12,message = "版本长度2-12")
    @NotNull(message = "版本不能为空",groups = {Insert.class,Update.class})
    private String version;

    /**
     * 环境变量
     */
    private List<AppEnvInfoDto> scriptEnv;

    /**
     * 脚本文件
     */
    private String scriptFile;

    /**
     * 介绍
     */
    @NotNull(message = "介绍不能为空",groups = {Insert.class,Update.class})
    private String description;

    @Serial
    private static final long serialVersionUID = 1L;
}
