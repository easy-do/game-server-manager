package plus.easydo.docker.model;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 容器端口映射参数封装
 * @date 2022/11/19
 */
@Data
public class PortBindDto {
    /**
     * 容器端口
     */
    private int containerPort;

    /**
     * 宿主机端口
     */
    private String localPort;

    /**
     * 网络协议
     */
    private String protocol;

}
