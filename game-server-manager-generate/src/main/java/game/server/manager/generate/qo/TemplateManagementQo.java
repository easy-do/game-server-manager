package game.server.manager.generate.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 模板管理查询对象
 *
 * @author gebilaoyu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class TemplateManagementQo {


    /**当前页*/
    protected Integer currentPage = 1;

    /** 每页显示条数 */
    protected Integer pageSize = 10;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 代码
     */
    private String templateCode;

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

}
