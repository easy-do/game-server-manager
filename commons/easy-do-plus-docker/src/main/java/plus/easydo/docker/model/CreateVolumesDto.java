package plus.easydo.docker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description docker创建存储卷参数封装
 * @date 2023/4/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVolumesDto {

    /** 新卷的名称。如果未指定，Docker 会生成一个名称 */
    private String name;

    /** 标签 */
    private Map<String, String> labels = new HashMap<>();

    /** 要使用的卷驱动程序的名称。名称默认为本地 */
    private String driver;

    /** 驱动程序选项和值的映射。这些选项直接传递给驱动程序并且是特定于驱动程序的 */
    private Map<String, String> driverOpts = new HashMap<>();
}
