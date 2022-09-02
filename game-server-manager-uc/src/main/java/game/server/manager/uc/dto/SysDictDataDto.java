package game.server.manager.uc.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典数据表
 * @author yuzhanfeng
 * @TableName sys_dict_data
 */
@Data
public class SysDictDataDto implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 字典类型
     */
    private Long dictTypeId;

    /**
     * 显示标签
     */
    private String dictLabel;

    /**
     * 字典排序
     */
    private Integer dictSort;

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
     * 备注
     */
    private String remark;

    @Serial
    private static final long serialVersionUID = 1L;
}