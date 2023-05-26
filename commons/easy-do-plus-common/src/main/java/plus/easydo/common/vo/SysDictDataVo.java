package plus.easydo.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典数据表
 * @author yuzhanfeng
 * @TableName sys_dict_data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDataVo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 字典类型
     */
    private Long dictTypeId;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 显示标签
     */
    private String dictLabel;

    /**
     * 字典标签
     */
    private String dictKey;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典键值类型
     */
    private String dictValueType;

    /**
     * 是否默认（0否 1是）
     */
    private Integer isDefault;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

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
     * 创建者
     */
    private Long createBy;


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
