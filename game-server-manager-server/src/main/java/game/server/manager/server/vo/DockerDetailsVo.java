package game.server.manager.server.vo;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import cn.hutool.core.date.DatePattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;

/**
 * docker配置信息数据展示对象
 * 
 * @author yuzhanfeng
 * @date 2022-11-13 12:10:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DockerDetailsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;
    /** 名称 */
    private String dockerName;
    /** ip地址 */
    private String dockerHost;
    /** 模式 api/client */
    private String dockerModel;
    /** client模式通信密钥 */
    private String dockerSecret;
    /** 证书 */
    private String dockerCert;
    /** 验证连接 */
    private Long dockerIsSsl;
    /** 证书密码 */
    private String dockerCertPassword;
    /** 创建人 */
    private Long createBy;
    /** 创建时间 */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;
    /** 更新人 */
    private Long updateBy;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** docker实时信息 */
    private String detailsJson;
    /** version详细信息 */
    private String versionJson;

}