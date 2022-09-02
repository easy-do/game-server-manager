package game.server.manager.server.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvModel {

    private String envKey;

    private String envValue;
}
