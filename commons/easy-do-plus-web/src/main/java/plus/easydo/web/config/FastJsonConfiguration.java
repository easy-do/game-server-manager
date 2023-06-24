//package plus.easydo.web.config;
//
//
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//
//import java.util.Arrays;
//
//
///**
// * @author laoyu
// * @version 1.0
// * @date 2022/9/4
// */
//@Configuration(proxyBeanMethods = false)
//public class FastJsonConfiguration {
//
//    @Bean
//    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        converter.setFastJsonConfig(new FastJsonConfig());
//        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, new MediaType("application", "*+json")));
//        return converter;
//    }
//
//}
