package game.server.manager.uc.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;


/**
 * 系统资源数据库映射对象
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_resource")
public class SysResource implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 资源名称 */
    private String resourceName;

    /** 资源编码 */
    private String resourceCode;

    /** 父级ID */
    private Long parentId;

    /** 资源类型 */
    private String resourceType;

    /** 显示顺序 */
    private Long orderNumber;

    /** 路径 */
    private String path;

    /** 参数 */
    private String param;

    /** 是否缓存 */
    private Long isCache;

    /** 是否隐藏 */
    private Integer visible;

    /** 状态 */
    private Integer status;

    /** 图标 */
    private String icon;

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 备注 */
    private String remark;

    /** 删除标记 */
    private Long delFlag;

}
