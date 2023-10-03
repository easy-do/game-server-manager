package plus.easydo.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description DemoService
 * @date 2023/7/6
 */
@Service("demo")
public class DemoService extends AbstractService{

    public Map<String, Object> getList(Map<String, Object> queryParam){
        return queryParam;
    }

    public Map<String, Object> getInfo(Map<String, Object> queryParam){
        return queryParam;
    }

    public Map<String, Object> add(Map<String, Object> queryParam){
        return queryParam;
    }


    public Map<String, Object> delete(Map<String, Object> queryParam){
        return queryParam;
    }
}
