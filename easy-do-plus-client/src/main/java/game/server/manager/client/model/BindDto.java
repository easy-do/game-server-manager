package plus.easydo.push.client.model;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 目录绑定参数封装
 * @date 2022/11/19
 */
@Data
public class BindDto {

    /**
     * 容器路径
     */
    private String containerPath;

    /**
     * 本地路径
     */
    private String localPath;
}
