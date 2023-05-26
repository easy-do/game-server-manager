package plus.easydo.api.feign.fallback;

import plus.easydo.api.feign.SysDictDataClient;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vo.SysDictDataVo;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
public class SysDictClientFallback implements SysDictDataClient {

    @Override
    public R<Object> listByCode(String dictCode) {
        return DataResult.fail();
    }

    @Override
    public R<SysDictDataVo> getSingleDictData(String dictCode, String dictDataKey) {
        return DataResult.fail();
    }
}

