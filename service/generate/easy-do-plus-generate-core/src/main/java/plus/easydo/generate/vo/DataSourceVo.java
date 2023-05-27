package plus.easydo.generate.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/4/14
 */
@Data
@Builder
public class DataSourceVo {

    /** 主键 */
    private Long id;

    /** 数据源名称 */
    private String sourceName;

    /** 数据源编码 */
    private String sourceCode;

    /** 数据源类型 */
    private String sourceType;

    /** 参数 */
    private String params;

    /** URL */
    private String url;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 状态(0停用 1启用) */
    private Integer status;

    /** 备注 */
    private String remark;

}
