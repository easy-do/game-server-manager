package game.server.manager.server.entity;

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
 * 脚本变量
 * @author yuzhanfeng
 * @TableName script_env_data
 */
@Data
@Builder
@TableName(value ="script_env_data")
@NoArgsConstructor
@AllArgsConstructor
public class ScriptEnvData implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属脚本id
     */
    private Long scriptId;

    /**
     * 变量名称
     */
    private String envName;

    /**
     * 变量key
     */
    private String envKey;

    /**
     * shell脚本取参key
     */
    private String shellKey;

    /**
     * 变量参数-默认值
     */
    private String envValue;

    /**
     * 变量类型
     */
    private String envType;

    /**
     * 描述
     */
    private String description;

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
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

}
