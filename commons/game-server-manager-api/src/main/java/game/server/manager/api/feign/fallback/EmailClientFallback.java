package game.server.manager.api.feign.fallback;

import game.server.manager.api.feign.EmailClient;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import org.springframework.stereotype.Service;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
public class EmailClientFallback implements EmailClient {

    @Override
    public R<String> sendHtmlMail(String email, String title, String content) {
        return DataResult.fail();
    }

    @Override
    public R<String> sendHtmlMailByUserId(Long userId, String title, String content) {
        return DataResult.fail();
    }
}
