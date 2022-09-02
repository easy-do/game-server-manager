package game.server.manager.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * APP信息
 * @author yuzhanfeng
 */
@Data
public class AppInfoVo implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * app名称
     */
    private String appName;

    /**
     * 版本
     */
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
     * 审核状态 1通过
     */
    private Integer isAudit;

    /**
     * 作用范围
     *
     */
    private String appScope;

    /**
     * 作者
     *
     */
    private String author;

    /**
     * 描述
     */
    private String description;

    /**
     * 热度
     */
    private Long heat;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}