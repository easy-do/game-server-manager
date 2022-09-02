package game.server.manager.server.dto;

import game.server.manager.common.vo.AppEnvInfoVo;
import lombok.Data;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author yuzhanfeng
 * @TableName app_script
 */
@Data
public class AppScriptDto implements Serializable {

    @NotNull(message = "id不能为空",groups = {Update.class})
    private Long id;

    /**
     * 适配应用
     */
    @NotNull(message = "适配应用不能为空",groups = {Insert.class,Update.class})
    private String adaptationAppId;

    /**
     * 适配应用名称
     */
    private String adaptationAppName;

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
     * 作用范围
     *
     */
    @NotNull(message = "作用范围不能为空",groups = {Insert.class,Update.class})
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