package game.server.manager.server.config.oss;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 文件包装类
 *
 * @author laoyu
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class OssObject<INDEX,FILE> {

    /** 文件唯一标识 */
    INDEX index;

    /** 文件 */
    @JsonIgnore
    FILE file;

    /** 文件名 */
    private String fileName;

    /** 类型 */
    private String fileType;

    /** 路径 */
    private String filePath;

    /** 分组名称 */
    private String groupName;

    /** 拓展信息 */
    private Map<String,String> expand;

}
