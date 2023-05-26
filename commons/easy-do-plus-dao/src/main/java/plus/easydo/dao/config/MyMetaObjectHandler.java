package plus.easydo.dao.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }
}
