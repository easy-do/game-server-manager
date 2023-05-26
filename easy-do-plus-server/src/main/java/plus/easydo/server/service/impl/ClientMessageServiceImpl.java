package plus.easydo.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.common.vo.ClientMessageVo;
import plus.easydo.server.entity.ClientMessage;
import plus.easydo.server.service.ClientMessageService;
import plus.easydo.server.mapper.ClientMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【client_message】的数据库操作Service实现
* @createDate 2022-08-07 21:14:10
*/
@Service
public class ClientMessageServiceImpl extends ServiceImpl<ClientMessageMapper, ClientMessage>
    implements ClientMessageService{

    @Override
    public List<ClientMessageVo> getNoConsumeMessage(String clientId) {
        LambdaQueryWrapper<ClientMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ClientMessage::getClientId,clientId);
        wrapper.eq(ClientMessage::getStatus,0);
        return BeanUtil.copyToList(list(wrapper),ClientMessageVo.class);
    }

    @Override
    public void removeByClientId(String clientId) {
        LambdaQueryWrapper<ClientMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ClientMessage::getClientId,clientId);
        remove(wrapper);
    }
}




