package game.server.manager.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 脚本环境变量返回结果封装
 * @date 2022/6/15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScriptEnvListVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    String scriptName;

    List<AppEnvInfoVo> env;
}
