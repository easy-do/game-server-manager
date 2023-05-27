package plus.easydo.server.config.oss.local;

import cn.hutool.core.io.FileUtil;
import plus.easydo.common.exception.OssException;
import plus.easydo.server.config.oss.OssObject;
import plus.easydo.server.config.oss.local.config.LocalOssProperties;
import plus.easydo.server.util.oss.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地文件存储工具类
 * @author laoyu
 * @version 1.0
 */
@Component
@EnableConfigurationProperties(LocalOssProperties.class)
public class LocalOssTemplate {
    
    private static String separator = File.separator;

    @Autowired
    LocalOssProperties localOssProperties;

    /**
     * 获得默认分组名称
     *
     * @return java.lang.String
     * @author laoyu
     */
    public String getDefaultGroupName(){
        return localOssProperties.getDefaultGroupName();
    }

    /**
     * 获得文件
     *
     * @param groupName groupName
     * @param fileIndex fileIndex
     * @return java.io.InputStream
     * @author laoyu
     */
    public InputStream getFile(String groupName, String fileIndex) {
        String storePath = localOssProperties.getStorePath();
        File file = FileUtil.file(storePath + separator + groupName + separator, fileIndex);
        return FileUtil.getInputStream(file);
    }

    /**
     * 删除文件
     *
     * @param groupName groupName
     * @param fileIndex fileIndex
     * @return java.lang.Boolean
     * @author laoyu
     */
    public Boolean remove(String groupName, String fileIndex) {
        String storePath = localOssProperties.getStorePath();
        return FileUtil.del(storePath + separator + groupName + separator + fileIndex);
    }


    /**
     * 复制文件到指定目标
     *
     * @param groupName groupName
     * @param index index
     * @param targetGroupName targetGroupName
     * @param targetIndex targetIndex
     * @return 新的文件信息
     * @author laoyu
     */
    public OssObject<String, MultipartFile> copy(String groupName, String index, String targetGroupName, String targetIndex) {
        String storePath = localOssProperties.getStorePath();
        String source = storePath + separator + groupName + separator + index;
        String target = storePath + separator + targetGroupName + separator + targetIndex;
        File file = FileUtil.copy(source,target, true);
        return OssUtil.buildOssStoreObject(groupName,file);
    }

    /**
     * 移动文件到指定路径
     *
     * @param groupName groupName
     * @param index index
     * @param targetGroupName targetGroupName
     * @param targetIndex targetIndex
     * @return  新的文件信息
     * @author laoyu
     */
    public OssObject<String, MultipartFile> move(String groupName, String index, String targetGroupName, String targetIndex) {
        String storePath = localOssProperties.getStorePath();
        String source = storePath + separator + groupName + separator + index;
        String target = storePath + separator + targetGroupName + separator + targetIndex;
        File file = FileUtil.file(source);
        File targetFile = FileUtil.file(target);
        FileUtil.move(file, targetFile, true);
        return OssUtil.buildOssStoreObject(groupName,FileUtil.file(target));
    }


    /**
     * 存储文件
     *
     * @param groupName groupName
     * @param filePath filePath
     * @param fileName fileName
     * @param file file
     * @author laoyu
     */
    public void save(String groupName, String filePath, String fileName, MultipartFile file) {
        String path = localOssProperties.getStorePath() + File.separator + groupName + File.separator + OssUtil.endWithSeparator(filePath);
        File files = FileUtil.file(path, fileName);
        try {
            FileUtil.writeFromStream(file.getInputStream(), files);
        } catch (IOException e) {
            e.printStackTrace();
            throw new OssException("存储文件发生异常");
        }
    }
}
