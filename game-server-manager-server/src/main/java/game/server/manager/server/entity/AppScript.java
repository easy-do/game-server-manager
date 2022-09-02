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
 * 
 * @author yuzhanfeng
 * @TableName app_script
 */
@TableName(value ="app_script")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppScript implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 适配应用名称
     */
    private String adaptationAppId;

    /**
     * 适配应用
     */
    private String adaptationAppName;

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
     * 作者
     */
    private String author;

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