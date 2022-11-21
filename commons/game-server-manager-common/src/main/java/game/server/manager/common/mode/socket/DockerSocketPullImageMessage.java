package game.server.manager.common.mode.socket;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description pull docker镜像参数封装
 * @date 2022/11/21
 */
@Data
public class DockerSocketPullImageMessage {

    private String dockerId;

    private String repository;

}
