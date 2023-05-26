package plus.easydo.uc.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 功能权限
 * @date 2023/3/11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionAuthVo {

    /**
     * 权限编码
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     * 显示顺序
     */
    private Long orderNumber;
}
