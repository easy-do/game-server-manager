package plus.easydo.server.entity;

import java.time.LocalDateTime;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;


/**
 * 应用版本信息数据库映射对象
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("application_version")
public class ApplicationVersion implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /** 应用id */
    private Long applicationId;

    /** 应用名称 */
    private String applicationName;

    /** 版本 */
    private String version;

    /** 状态 0草稿 1发布 2审核 3审核失败 */
    private Integer status;

    /** 详情 */
    private String description;

    /** 热度 */
    private Long heat;

    /** 配置信息 */
    private String confData;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

    /** 创建人 */
    private Long createBy;

    /** 更新人 */
    private Long updateBy;

    /** 删除标记 */
    private Long delFlag;

}
