package game.server.manager.server.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 应用版本信息数据传输对象
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ApplicationVersionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 自增主键 */

    private Long id;

    /** 应用id */
    private Long applicationId;

    /** 应用名称 */

    private String applicationName;

    /** 版本 */

    @NotNull(message = "版本必填",groups = {Insert.class, Update.class})
    private String version;

    /** 状态 0草稿 1发布 2审核 3审核失败 */

    private Integer status;

    /** 详情 */

    private String description;

    /** 热度 */

    private Long heat;

    /** 配置信息 */

    private String confData;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建人 */
    private Long createBy;

    /** 更新人 */
    private Long updateBy;

    /** 删除标记 */
    private Long delFlag;

}