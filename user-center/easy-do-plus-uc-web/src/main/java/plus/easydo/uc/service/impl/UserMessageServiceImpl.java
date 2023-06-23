package plus.easydo.uc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.uc.entity.UserMessage;
import plus.easydo.uc.mapper.UserMessageMapper;
import plus.easydo.uc.service.UserMessageService;
import org.springframework.stereotype.Service;

/**
* @author yuzhanfeng
* @description 针对表【user_message(用户消息)】的数据库操作Service实现
* @createDate 2022-07-09 23:37:47
*/
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage>
    implements UserMessageService {

}




