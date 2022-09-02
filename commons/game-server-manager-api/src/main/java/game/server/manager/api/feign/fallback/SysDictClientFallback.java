package game.server.manager.api.feign.fallback;

import game.server.manager.api.feign.SysDictDataClient;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.common.vo.SysDictDataVo;
import org.springframework.stereotype.Service;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
public class SysDictClientFallback implements  SysDictDataClient {

    @Override
    public R<Object> listByCode(String dictCode) {
        return DataResult.fail();
    }

    @Override
    public R<SysDictDataVo> getSingleDictData(String dictCode, String dictDataKey) {
        return DataResult.fail();
    }
}

