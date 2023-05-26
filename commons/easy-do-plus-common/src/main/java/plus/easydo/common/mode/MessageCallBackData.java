package plus.easydo.common.mode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageCallBackData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String clientId;

    private Long messageId;

    private Integer status;

    private Integer messageType;
}
