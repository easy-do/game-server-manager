package plus.easydo.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScriptStartEvent {

    private Long userId;

    private Long appScriptId;

}
