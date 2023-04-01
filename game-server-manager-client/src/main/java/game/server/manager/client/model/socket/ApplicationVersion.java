package game.server.manager.client.model.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;


/**
 * 应用版本信息数据库映射对象
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersion implements Serializable {


    private Long id;

    /** 应用id */
    private Long applicationId;

    /** 应用名称 */
    private String applicationName;

    /** 版本 */
    private String version;

    /** 状态 0草稿 1发布 2审核 3审核失败 */
    private Integer status;

    /** 详情 */
    private String description;

    /** 热度 */
    private Long heat;

    /** 配置信息 */
    private String confData;

}
