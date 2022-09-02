package game.server.manager.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典类型表
 * @author yuzhanfeng
 * @TableName sys_dict_type
 */
@Data
public class SysDictTypeVo implements Serializable {
    /**
     * 字典主键
     */
    private Long id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典标识
     */
    private String dictCode;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 备注
     */
    private String remark;

    @Serial
    private static final long serialVersionUID = 1L;
}