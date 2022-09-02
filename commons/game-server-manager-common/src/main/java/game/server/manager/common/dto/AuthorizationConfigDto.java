package game.server.manager.common.dto;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */

@Data
public class AuthorizationConfigDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String application;

    @JSONField(format = DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime expires;

    private Long serverNum;

    private Long applicationNum;

    private Long appCreationNum;

    private Long scriptCreationNum;

    private Long genNum;

    private Integer state;

    private String description;
}
