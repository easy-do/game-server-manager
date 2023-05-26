package plus.easydo.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/19
 */
@Data
public class ServerInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 服务地址
     */
    private String address;

    /**
     * 端口
     */
    private String port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;


}
