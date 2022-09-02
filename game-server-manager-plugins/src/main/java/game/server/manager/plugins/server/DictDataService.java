package game.server.manager.plugins.server;

import com.alibaba.fastjson2.JSON;
import game.server.manager.plugins.config.SystemUtils;
import game.server.manager.common.constant.PathConstants;
import game.server.manager.common.result.R;
import game.server.manager.common.utils.http.HttpModel;
import game.server.manager.common.utils.http.HttpRequestUtil;
import game.server.manager.common.vo.SysDictDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author laoyu
 * @version 1.0
 */
@Component
public class DictDataService {

    @Autowired
    private SystemUtils systemUtils;

    public String getValue(String dictCode, String dictDataKey){
        String path = PathConstants.GET_SINGLE_DICT_DATA.replace("{dictCode}",dictCode).replace("{dictDataKey}",dictDataKey);
        String resultStr = HttpRequestUtil.get(HttpModel.builder().host(systemUtils.getManagerUrl()).path(path).build());
        R<String> result = HttpRequestUtil.unPackage(resultStr);
        SysDictDataVo sysDictDataVo = JSON.parseObject(result.getData(),SysDictDataVo.class);
        return sysDictDataVo.getDictValue();
    }
}
