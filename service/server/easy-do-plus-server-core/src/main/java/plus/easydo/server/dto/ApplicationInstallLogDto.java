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
 * 应用安装日志数据传输对象
 * 
 * @author yuzhanfeng
 * @date 2023-04-08 18:35:18
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ApplicationInstallLogDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private String id;

    /** 应用id */
    private Long applicationId;

    /** 应用名称 */

    @NotNull(message = "应用名称必填",groups = {Insert.class, Update.class})
    private String applicationName;

    /** 版本 */

    @NotNull(message = "版本必填",groups = {Insert.class, Update.class})
    private String version;

    /** 客户端id */
    private String clientId;

    /** 客户端 */

    @NotNull(message = "客户端必填",groups = {Insert.class, Update.class})
    private String clientName;

    /** 开始时间 */

    @NotNull(message = "开始时间必填",groups = {Insert.class, Update.class})
    private LocalDateTime startTime;

    /** 结束时间 */

    @NotNull(message = "结束时间必填",groups = {Insert.class, Update.class})
    private LocalDateTime endTime;

    /** 日志数据 */
    private String logData;

    /** 状态 */

    @NotNull(message = "状态必填",groups = {Insert.class, Update.class})
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 创建人 */
    private Long createBy;

    /** 删除标记 */
    private Integer delFlag;

}
