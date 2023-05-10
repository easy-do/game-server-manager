package game.server.manager.server.vo.server;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import cn.hutool.core.date.DatePattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;

/**
 * 应用信息数据展示对象
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
public class ApplicationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;
    /** 应用名称 */
    private String applicationName;
    /** 状态 0草稿 1发布 2审核 3审核失败 */
    private Integer status;
    /** 图标 */
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
