package game.server.manager.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.common.vo.ClientMessageVo;
import game.server.manager.server.entity.ClientMessage;
import game.server.manager.server.service.ClientMessageService;
import game.server.manager.server.mapper.ClientMessageMapper;
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




