package plus.easydo.server.vo.server;

import java.time.LocalDateTime;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;

/**
 * 应用版本信息数据展示对象
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
public class ApplicationVersionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
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
    /** 创建时间 */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;
    /** 更新时间 */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;
    /** 创建人 */
    private Long createBy;
    /** 更新人 */
    private Long updateBy;
    /** 删除标记 */
    private Long delFlag;

}
