package plus.easydo.uc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典数据表
 * @author yuzhanfeng
 * @TableName sys_dict_data
 */
@TableName(value ="sys_dict_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDictData implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
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

}
