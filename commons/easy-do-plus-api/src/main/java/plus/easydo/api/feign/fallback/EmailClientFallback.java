package plus.easydo.api.feign.fallback;

import plus.easydo.api.feign.EmailClient;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

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
