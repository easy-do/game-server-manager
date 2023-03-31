package game.server.manager.client.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.server.manager.client.config.SystemUtils;
import game.server.manager.client.contants.PathConstants;
import game.server.manager.client.model.SysDictDataVo;
import game.server.manager.client.result.R;
import game.server.manager.client.utils.http.HttpModel;
import game.server.manager.client.utils.http.HttpRequestUtil;
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
        ObjectMapper objectMapper = new ObjectMapper();
        SysDictDataVo sysDictDataVo = null;
        try {
            sysDictDataVo = objectMapper.readValue(result.getData(), SysDictDataVo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return sysDictDataVo.getDictValue();
    }
}
