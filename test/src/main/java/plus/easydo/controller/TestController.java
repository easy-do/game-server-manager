package plus.easydo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.uc.api.OauthClientDetailsApi;

import java.util.HashMap;

/**
 * @author laoyu
 * @version 1.0
 * @description TODO
 * @date 2023/6/24
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OauthClientDetailsApi oauthClientDetailsApi;

    @GetMapping("/testApi")
    public Object testApi() {
        return oauthClientDetailsApi.info(1L);
    }

}
