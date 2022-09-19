package game.server.manager.uc.dto;

import java.time.LocalDateTime;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 系统资源数据传输对象
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SysResourceDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 资源名称 */

    @NotNull(message = "资源名称必填",groups = {Insert.class, Update.class})
    private String resourceName;

    /** 资源编码 */

    @NotNull(message = "资源编码必填",groups = {Insert.class, Update.class})
    private String resourceCode;

    /** 父级ID */
    private Long parentId;

    /** 资源类型 */

    @NotNull(message = "资源类型必填",groups = {Insert.class, Update.class})
    private String resourceType;

    /** 显示顺序 */
    private Long orderNumber;

    /** 路径 */

    @NotNull(message = "路径必填",groups = {Insert.class, Update.class})
    private String path;

    /** 参数 */
    private String param;

    /** 是否缓存 */

    @NotNull(message = "是否缓存必填",groups = {Insert.class, Update.class})
    private Long isCache;

    /** 状态 */

    @NotNull(message = "状态必填",groups = {Insert.class, Update.class})
    private Long status;

    /** 图标 */

    @NotNull(message = "图标必填",groups = {Insert.class, Update.class})
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

    @NotNull(message = "备注必填",groups = {Insert.class, Update.class})
    private String remark;

    /** 删除标记 */
    private Long delFlag;

}
