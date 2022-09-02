package game.server.manager.generate.dynamic.core.qo;

import lombok.Data;

/**
 * 数据源管理查询对象
 * 
 * @author gebilaoyu
 */
@Data
public class DataSourceQo {

    /**当前页*/
    protected Integer currentPage = 1;

    /** 每页显示条数 */
    protected Integer pageSize = 10;

    /** 主键 */
    private String id;

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
    private Integer state;

}
