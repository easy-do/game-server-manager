package plus.easydo.push.client.model;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 容器连接参数封装
 * @date 2022/11/19
 */
@Data
public class LinkDto {

    /**
     * 容器名
     */
    private String name;

    /**
     * 别名
     */
    private String alis;
}
