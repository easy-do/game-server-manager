package game.server.manager.uc.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典类型表
 * @author yuzhanfeng
 * @TableName sys_dict_type
 */
@Data
public class SysDictTypeDto implements Serializable {
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
     * 备注
     */
    private String remark;

    @Serial
    private static final long serialVersionUID = 1L;
}