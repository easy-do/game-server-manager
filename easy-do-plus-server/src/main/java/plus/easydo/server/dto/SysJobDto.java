package plus.easydo.server.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 定时任务调度表
 * @author yuzhanfeng
 */
@Data
public class SysJobDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @NotNull(message = "id不能为空",groups = {Update.class})
    private Long jobId;

    /**
     * 任务名称
     */
    @NotNull(message = "任务名称不能为空",groups = {Insert.class, Update.class})
    private String jobName;

    /**
     * cron执行表达式
     */
    @NotNull(message = "执行表达式不能为空",groups = {Insert.class, Update.class})
    private String cronExpression;

    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @NotNull(message = "计划执行策略不能为空",groups = {Insert.class, Update.class})
    private String misfirePolicy;

    /**
     * 状态（0正常 1暂停）
     */
    @NotNull(message = "状态不能为空",groups = {Insert.class, Update.class})
    private String status;

    /**
     * 任务类型
     */
    @NotNull(message = "任务类型不能为空",groups = {Insert.class, Update.class})
    private String jobType;

    /**
     * 备注信息
     */
    @NotNull(message = "备注不能为空",groups = {Insert.class, Update.class})
    private String remark;


    /**
     * 脚本id
     */
    @NotNull(message = "脚本不能为空",groups = {Insert.class, Update.class})
    private Long appScriptId;

    /**
     * 服务器id
     */
    @NotNull(message = "服务器不能为空",groups = {Insert.class, Update.class})
    private Long serverId;

    /**
     * 脚本参数
     */
    private JSONObject env;

}
