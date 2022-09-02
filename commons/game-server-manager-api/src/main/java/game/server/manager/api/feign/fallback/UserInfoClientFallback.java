package game.server.manager.api.feign.fallback;

import game.server.manager.api.feign.UserInfoClient;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.UserInfoVo;
import org.springframework.stereotype.Service;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
@Service
public class UserInfoClientFallback implements UserInfoClient {
    @Override
    public R<UserInfoVo> getUserInfo(Long userId) {
        return DataResult.fail();
    }

    @Override
    public R<Long> count() {
        return DataResult.fail();
    }

    @Override
    public R<String> avatar(Long id) {
        return DataResult.fail();
    }
}
