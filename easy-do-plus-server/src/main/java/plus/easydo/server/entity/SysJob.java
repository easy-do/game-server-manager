package plus.easydo.server.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 定时任务调度表
 * @author yuzhanfeng
 * @TableName sys_job
 */
@TableName(value ="sys_job")
@Data
public class SysJob implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(type = IdType.AUTO)
    private Long jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务类型
     */
    private String jobType;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * 任务参数
     */
    private String jobParam;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    private String misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    private String concurrent;

    /**
     * 状态（0正常 1暂停）
     */
    private String status;

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
    private String updateBy;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 删除标记
     */
    @TableLogic
    private Integer delFlag;

}
