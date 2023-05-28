package plus.easydo.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
