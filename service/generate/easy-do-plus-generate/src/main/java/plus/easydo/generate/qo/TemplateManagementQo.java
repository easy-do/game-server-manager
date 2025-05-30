package plus.easydo.generate.qo;

import plus.easydo.generate.entity.TemplateManagement;
import plus.easydo.dao.qo.MpBaseQo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 模板管理查询对象
 *
 * @author gebilaoyu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class TemplateManagementQo extends MpBaseQo<TemplateManagement> {

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
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

}
