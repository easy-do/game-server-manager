package plus.easydo.uc.vo;

import cn.hutool.core.date.DatePattern;
import cn.zhxu.bs.bean.SearchBean;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 授权码
 * @author yuzhanfeng
 * @TableName authorization_code
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SearchBean(tables = "authorization_code")
public class AuthorizationCodeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 授权码
     */
    private String code;

    /**
     * 状态 0未使用 1使用
     */
    private Integer state;

    /**
     * 配置信息
     */
    private String config;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @JSONField(format = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
