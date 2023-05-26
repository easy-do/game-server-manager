package plus.easydo.api;

import plus.easydo.common.dto.UserMessageDto;
import plus.easydo.common.result.R;
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
     * @return result.plus.easydo.common.R<java.lang.Long>
     * @author laoyu
     * @date 2022/8/31
     */
    @PostMapping("/insert")
    R<Long> insert(@RequestBody UserMessageDto userMessageDto);
}
