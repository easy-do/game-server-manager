package plus.easydo.server.service;

import plus.easydo.common.vo.ClientMessageVo;
import plus.easydo.server.entity.ClientMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @author yuzhanfeng
 * @description 针对表【client_message】的数据库操作Service
 * @createDate 2022-08-07 21:14:10
 */
public interface ClientMessageService extends IService<ClientMessage> {

    /**
     * 获取客户端未消费的消息
     *
     * @param clientId clientId
     * @return java.util.List<vo.plus.easydo.common.ClientMessageVo>
     * @author laoyu
     * @date 2022/8/7
     */
    List<ClientMessageVo> getNoConsumeMessage(String clientId);

    /**
     * 删除客户端消息
     *
     * @param clientId clientId
     * @author laoyu
     * @date 2022/8/10
     */
    void removeByClientId(String clientId);
}
