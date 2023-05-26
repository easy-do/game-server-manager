package plus.easydo.common.result;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author laoyu
 * @version 1.0
 */
@Data
public class OssResult<INDEX> {

    /** 文件标识 */
    private INDEX index;

    /** 文件名 */
    private String fileName;

    /** 路径 */
    private String filePath;

    /** 文件大小/kb */
    private BigDecimal fileSize;

    /** 存储桶 */
    private String groupName;

    /** 拼接好的路径 */
    private String url;
}
