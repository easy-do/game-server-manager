package game.server.manager.server.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APP信息
 * @author yuzhanfeng
 * @TableName app_info
 */
@TableName(value ="app_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
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
     * 图标
     */
    private String icon;

    /**
     * 图片
     */
    private String picture;

    /**
     * 作者
     */
    private String author;

    /**
     * 状态
     */
    private Integer state;

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
     * 部署方式
     */
    private Integer deployType;

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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 删除标记
     */
    @TableLogic
    private Integer delFlag;

}