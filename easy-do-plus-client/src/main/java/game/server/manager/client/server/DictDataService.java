package plus.easydo.client.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import plus.easydo.client.config.JacksonObjectMapper;
import plus.easydo.client.config.SystemUtils;
import plus.easydo.client.contants.PathConstants;
import plus.easydo.client.model.SysDictDataVo;
import plus.easydo.client.result.R;
import plus.easydo.client.utils.http.HttpModel;
import plus.easydo.client.utils.http.HttpRequestUtil;
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

    @Autowired
    private JacksonObjectMapper mapper;

    public String getValue(String dictCode, String dictDataKey){
        String path = PathConstants.GET_SINGLE_DICT_DATA.replace("{dictCode}",dictCode).replace("{dictDataKey}",dictDataKey);
        String resultStr = HttpRequestUtil.get(HttpModel.builder().host(systemUtils.getManagerUrl()).path(path).build());
        R<String> result = HttpRequestUtil.unPackage(resultStr);
        SysDictDataVo sysDictDataVo = null;
        try {
            sysDictDataVo = mapper.readValue(result.getData(), SysDictDataVo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return sysDictDataVo.getDictValue();
    }
}
