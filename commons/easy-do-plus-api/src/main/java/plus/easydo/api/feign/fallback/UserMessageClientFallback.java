package plus.easydo.api.feign.fallback;

import plus.easydo.api.feign.UserMessageClient;
import plus.easydo.common.dto.UserMessageDto;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
public class UserMessageClientFallback implements UserMessageClient {

    @Override
    public R<Long> insert(UserMessageDto userMessageDto) {
        return DataResult.fail();
    }
}
