package plus.easydo.server.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 应用信息数据传输对象
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ApplicationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 应用名称 */

    @NotNull(message = "应用名称必填",groups = {Insert.class, Update.class})
    private String applicationName;

    /** 状态 0草稿 1发布 2审核 3审核失败 */
    private Integer status;

    /** 图标 */

    @NotNull(message = "图标必填",groups = {Insert.class, Update.class})
    private String icon;

    /** 作者 */

    private String author;

    /** 可见范围 0 私有 1公开 */

    private Integer scope;

    /** 描述 */
    private String description;

    /** 热度 */

    private Long heat;

    /** 创建时间 */

    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建人 */
    private Long createBy;

    /** 更新人 */
    private Long updateBy;

}
