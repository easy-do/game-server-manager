package game.server.manager.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author laoyu
 * @version 1.0
 * @description 安装应用参数封装
 * @date 2023/3/19
 */

@NoArgsConstructor
@Data
public class InstallApplicationDto {

    private Long version;
    private String clientId;
    private String confData;
    private Long applicationId;

}
