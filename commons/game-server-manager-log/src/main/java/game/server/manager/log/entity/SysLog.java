package game.server.manager.log.entity;

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
 * 系统日志
 * @author yuzhanfeng
 * @TableName sys_log
 */
@TableName(value ="sys_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLog implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String data;

    /**
     * 开始时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}