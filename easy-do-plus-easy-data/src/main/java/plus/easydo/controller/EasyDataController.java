package plus.easydo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.service.AbstractService;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description EasyDataController
 * @date 2023/7/6
 */

@RequestMapping("/ed")
@RestController
public class EasyDataController {

    @Autowired
    private Map<String, AbstractService> serviceMap;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @GetMapping("/{service}/{method}")
    public Object get(@PathVariable("service") String service, @PathVariable("method") String method,@RequestParam(required = false) Map<String, Object> queryParam) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        AbstractService serviceInstance = serviceMap.get(service);
        return serviceInstance.execGet(method, queryParam);
    }


    @PostMapping("/{service}/{method}")
    public Object post(@PathVariable("service") String service, @PathVariable("method") String method,@RequestBody Map<String,Object> queryParam) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        AbstractService serviceInstance = serviceMap.get(service);
        return serviceInstance.execPost(method, queryParam);
    }

    @DeleteMapping("/{service}")
    public Object delete(@PathVariable("service") String service, @RequestParam(required = false) Map<String, Object> queryParam) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        AbstractService serviceInstance = serviceMap.get(service);
        return serviceInstance.execDelete(queryParam);
    }

    @DeleteMapping("/{service}/{method}")
    public Object delete(@PathVariable("service") String service, @PathVariable("method") String method,@RequestParam(required = false) Map<String, Object> queryParam) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        AbstractService serviceInstance = serviceMap.get(service);
        return serviceInstance.execDelete(method, queryParam);
    }


}
