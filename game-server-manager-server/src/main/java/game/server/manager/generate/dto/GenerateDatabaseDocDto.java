package game.server.manager.generate.dto;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 */
@Data
public class GenerateDatabaseDocDto {

    /**
     * 表名集合
     */
    private String tables;

    /**
     * 数据源id
     */
    private String dataSourceId;

    /**
     * 模板集合
     */
    private String templateId;
}
