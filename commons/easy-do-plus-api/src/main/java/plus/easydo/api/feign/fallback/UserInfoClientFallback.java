package plus.easydo.api.feign.fallback;

import plus.easydo.api.feign.UserInfoClient;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vo.UserInfoVo;
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
