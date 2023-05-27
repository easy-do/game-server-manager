package plus.easydo.generate.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 模板管理数据传输对象
 *
 * @author gebilaoyu
 */
@Data
@Builder
public class TemplateManagementDto implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 类型
     */
    private String templateType;

    /**
     * 可见范围
     */
    private String templateScope;

    /**
     * 版本
     */
    private String version;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 描述
     */
    private String description;

}
