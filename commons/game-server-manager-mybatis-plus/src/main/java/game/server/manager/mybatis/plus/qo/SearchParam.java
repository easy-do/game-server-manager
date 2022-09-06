package game.server.manager.mybatis.plus.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 查询参数
 * @date 2022/9/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParam {

    private String column;

    private String searChType;

}
