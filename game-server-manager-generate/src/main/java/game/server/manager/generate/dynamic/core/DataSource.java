package game.server.manager.generate.dynamic.core;


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
public class DataSource {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /** 数据源名称 */
    @TableField(value = "source_name")
    private String sourceName;

    /** 数据源编码 */
    @TableField(value = "source_code")
    private String sourceCode;

    /** 数据源类型 */
    @TableField(value = "source_type")
    private String sourceType;

    /** 参数 */
    @TableField(value = "params")
    private String params;

    /** URL */
    @TableField(value = "url")
    private String url;

    /** 用户名 */
    @TableField(value = "user_name")
    private String userName;

    /** 密码 */
    @TableField(value = "password")
    private String password;

    /** 状态(0停用 1启用) */
    @TableField(value = "state")
    private Integer state;

    /** 备注 */
    @TableField(value = "state")
    private String remark;

    /** 删除标记 */
    @TableLogic
    @TableField(
            fill = FieldFill.INSERT
    )
    private Integer isDeleted;

    /** 创建时间 */
    @TableField(
            fill = FieldFill.INSERT
    )
    private LocalDateTime gmtCreate;

    /** 更新时间 */
    @TableField(
            fill = FieldFill.INSERT_UPDATE
    )
    private LocalDateTime gmtModified;

    /** 创建人 */
    @TableField(
            fill = FieldFill.INSERT
    )
    private String creator;

    /** 修改人 */
    @TableField(
            fill = FieldFill.INSERT_UPDATE
    )
    private String modifier;

}
