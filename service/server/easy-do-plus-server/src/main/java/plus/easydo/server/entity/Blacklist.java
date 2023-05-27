package plus.easydo.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * IP黑名单
 * @author yuzhanfeng
 * @TableName blacklist
 */
@TableName(value ="blacklist")
@Data
public class Blacklist implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * IP
     */
    private String ip;

    /**
     * 黑名单结束时间
     */
    private LocalDateTime disableTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除(1未删除；0已删除)
     */
    private Integer delFlag;


}
