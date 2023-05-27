package plus.easydo.server.service.oss;

import plus.easydo.common.result.OssResult;
import plus.easydo.common.result.R;
import plus.easydo.server.config.oss.OssObject;

import java.math.BigDecimal;

/**
 * @author laoyu
 * @version 1.0
 * @description 文件存储抽象接口
 * @date 2022/9/24
 */

public interface OssStoreService <INDEX, INPUT_FILE, RESULT_FILE, TEMPLATE> {


    /**
     * 获得工具类
     *
     * @return TOOL
     * @author laoyu
     */
    TEMPLATE getTemplate();

    /**
     * 功能描述
     *
     * @param ossObject 文件包装类
     * @return 文件
     * @author laoyu
     */
    R<OssResult<INDEX>> save(OssObject<INDEX, INPUT_FILE> ossObject);


    /**
     * 获得文件
     * @param groupName 分组
     * @param index 文件唯一标识
     * @return 文件包装类
     * @author laoyu
     */
    RESULT_FILE getFile(String groupName, INDEX index);

    /**
     * 功能描述
     *
     * @param groupName 分组
     * @param index 文件标识
     * @return 是否删除成功
     * @author laoyu
     */
    Boolean remove(String groupName, INDEX index);


    /**
     * 复制文件
     *
     * @param groupName 分组
     * @param index 文件索引
     * @param targetGroupName 目标分组
     * @param targetIndex 目标索引
     * @author laoyu
     */
    void copy(String groupName, INDEX index, String targetGroupName, INDEX targetIndex);

    /**
     * 移动文件
     *
     * @param groupName 分组
     * @param index 文件索引
     * @param targetGroupName 目标分组
     * @param targetIndex 目标索引
     * @author laoyu
     */
    void move(String groupName, INDEX index, String targetGroupName, INDEX targetIndex);


    /**
     * 校验文件
     *
     * @param inputFile inputFile
     * @author laoyu
     */
    void validationFile(INPUT_FILE inputFile);

    /**
     * 构建文件路径
     *
     * @param filePath filePath
     * @return java.lang.String
     * @author laoyu
     */
    String buildFilePath(String filePath);

    /**
     * 构建文件名称
     *
     * @param inputFile inputFile
     * @param fileName fileName
     * @return java.lang.String
     * @author laoyu
     */
    String buildFileName(INPUT_FILE inputFile,String fileName);

    /**
     * 构建文件分组名称
     *
     * @param groupName groupName
     * @return java.lang.String
     * @author laoyu
     */
    String buildGroupName(String groupName);


    /**
     * 获得文件大小
     *
     * @param inputFile inputFile
     * @return java.math.BigDecimal
     * @author laoyu
     */
    BigDecimal getFileKbSize(INPUT_FILE inputFile);


    /**
     * 构建文件返回对象
     *
     * @return INPUT_FILE
     * @author laoyu
     */
    OssResult<INDEX> buildResult();

}
