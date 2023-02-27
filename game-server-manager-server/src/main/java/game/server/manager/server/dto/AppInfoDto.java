package game.server.manager.server.dto;

import lombok.Data;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * APP信息
 * @author yuzhanfeng
 */
@Data
public class AppInfoDto implements Serializable {
    /**
     * 自增主键
     */
    @NotNull(message = "id不能为空",groups = {Update.class})
    private Long id;

    /**
     * app名称
     */
    @Size(min = 2,max = 12,message = "app名称长度2-32")
    @NotNull(message = "app名称不能为空",groups = {Insert.class,Update.class})
    private String appName;

    /**
     * 版本
     */
    @Size(min = 2,max = 12,message = "版本长度2-12")
    @NotNull(message = "版本不能为空",groups = {Insert.class,Update.class})
    private String version;

    /**
     * 自定义启动命令
     */
    private String startCmd;

    /**
     * 停止命令
     */
    private String stopCmd;

    /**
     * 配置文件路径
     */
    private String configFilePath;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空",groups = {Insert.class,Update.class})
    private Integer state;

    /**
     * 图标
     */
    private String icon;

    /**
     * 图片
     */
    private String picture;

    /**
     * 作用范围
     *
     */
    @NotNull(message = "类型不能为空",groups = {Insert.class,Update.class})
    private String appScope;

    /**
     * 描述
     */
    @NotNull(message = "简介不能为空",groups = {Insert.class,Update.class})
    private String description;

    /**
     * 环境变量
     */
    private List<AppEnvInfoDto> appEnv;


    @Serial
    private static final long serialVersionUID = 1L;
}
