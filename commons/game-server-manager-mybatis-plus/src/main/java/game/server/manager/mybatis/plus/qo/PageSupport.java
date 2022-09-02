package game.server.manager.mybatis.plus.qo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/20
 */

@Data
@Component
public class PageSupport {

    /**
     * 开关
     */
    @Value("${system.page.enable:false}")
    private Boolean enable;

    /**
     * 页码字段
     */
    @Value("${system.page.current-page-filed:pageNum}")
    private String pageNumFiled;

    /**
     * 每页显示条数字段
     */
    @Value("${system.page.page-size-filed:pageSize}")
    private String pageSizeFiled;
}
