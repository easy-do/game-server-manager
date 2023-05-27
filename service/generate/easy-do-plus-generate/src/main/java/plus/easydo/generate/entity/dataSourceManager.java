package plus.easydo.generate.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据源管理数据库映射对象
 *
 * @author gebilaoyu
 */
@Data
@TableName("data_source_manager")
public class dataSourceManager {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 数据源名称 */
    private String sourceName;

    /** 数据源编码 */
    private String sourceCode;

    /** 数据源类型 */
    private String sourceType;

    /** 参数 */
    private String params;

    /** URL */
    private String url;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 状态(0停用 1启用) */
    private Integer status;

    /** 备注 */
    private String remark;

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
