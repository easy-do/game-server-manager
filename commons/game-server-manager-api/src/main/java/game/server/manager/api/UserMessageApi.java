package game.server.manager.api;

import game.server.manager.common.dto.UserMessageDto;
import game.server.manager.common.result.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yuzhanfeng
 */
public interface UserMessageApi {

    /**
     * 发送用户消息
     *
     * @param userMessageDto userMessageDto
     * @return game.server.manager.common.result.R<java.lang.Long>
     * @author laoyu
     * @date 2022/8/31
     */
    @PostMapping("/insert")
    R<Long> insert(@RequestBody UserMessageDto userMessageDto);
}
