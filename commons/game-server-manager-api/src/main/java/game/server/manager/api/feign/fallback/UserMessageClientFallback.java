package game.server.manager.api.feign.fallback;

import game.server.manager.api.feign.UserMessageClient;
import game.server.manager.common.dto.UserMessageDto;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

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
