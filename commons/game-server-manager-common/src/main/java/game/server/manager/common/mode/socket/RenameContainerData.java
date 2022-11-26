package game.server.manager.common.mode.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuzhanfeng
 * @Date 2022/11/27 6:54
 * @Description 重命名容器参数封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenameContainerData {

    private String containerId;

    private String name;
}
