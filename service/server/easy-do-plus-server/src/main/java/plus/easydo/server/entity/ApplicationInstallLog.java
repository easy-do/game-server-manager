package plus.easydo.server.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;


/**
 * 应用安装日志数据库映射对象
 * 
 * @author yuzhanfeng
 * @date 2023-04-08 18:35:18
 */
@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("application_install_log")
public class ApplicationInstallLog implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private String id;

    /** 应用id */
    private Long applicationId;

    /** 应用名称 */
    private String applicationName;

    /** 版本 */
    private String version;

    /** 客户端id */
    private String clientId;

    /** 客户端 */
    private String clientName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endTime;

    /** 日志数据 */
    private String logData;

    /** 状态 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 创建人 */
    private Long createBy;

    /** 删除标记 */
    private Integer delFlag;

}
