package plus.easydo.generate.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 代码生成树对象
 * @date 2023/5/27
 */
@Data
@Builder
public class CodePathTree {

    private Integer id;

    private Integer parentId;

    private String path;
}
