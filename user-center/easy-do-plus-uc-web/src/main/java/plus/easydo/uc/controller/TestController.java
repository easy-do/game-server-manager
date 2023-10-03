//package plus.easydo.uc.controller;
//
//import cn.zhxu.bs.BeanSearcher;
//import cn.zhxu.bs.MapSearcher;
//import cn.zhxu.bs.SearchResult;
//import plus.easydo.uc.entity.UserInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
///**
// * @author laoyu
// * @version 1.0
// * @description TODO
// * @date 2023/5/25
// */
//
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//    @Autowired
//    private BeanSearcher beanSearcher;
//
//    @Autowired
//    private MapSearcher mapSearcher;
//
//    @GetMapping("/mapSearcher")
//    public SearchResult<Map<String, Object>> mapSearcher(@RequestParam(required = false) Map<String,Object> map) {
//        return mapSearcher.search(UserInfo.class,map);
//    }
//    @GetMapping("/beanSearcher")
//    public SearchResult<UserInfo> beanSearcher(@RequestParam(required = false) Map<String,Object> map) {
//        return beanSearcher.search(UserInfo.class,map);
//    }
//
//
//
//}
