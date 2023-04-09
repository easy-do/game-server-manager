package game.server.manager.client.model.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 安装日志返回结果封装
 * @date 2023/4/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstallLogResultData {
    

    private String message;

    /** true正常 false结束 */
    private Boolean status;
}
