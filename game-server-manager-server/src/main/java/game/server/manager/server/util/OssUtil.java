package game.server.manager.server.util;

import cn.hutool.core.text.CharSequenceUtil;
import game.server.manager.server.config.oss.OssObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 工具类
 * @author laoyu
 * @version 1.0
 */
public class OssUtil {


    public static final String SLASH = "/";

    /**
     * 构建OssStoreObject对象
     *
     * @param groupName groupName
     * @param file file
     * @return plus.easydo.starter.file.FileStoreObjec
     * @author laoyu
     */
    public static OssObject<String, MultipartFile> buildOssStoreObject(String groupName, File file){
        OssObject<String,MultipartFile> ossObject = new OssObject<>();
        ossObject.setIndex(file.getName());
        ossObject.setFile(fileToMultipartFile(file));
        ossObject.setFileName(file.getName());
        ossObject.setGroupName(groupName);
        ossObject.setFilePath(file.getPath());
        return ossObject;
    }


    /**
     * 字符串是否以文件分隔符为后缀
     *
     * @param str str
     * @return java.lang.String
     * @author laoyu
     */
    public static String endWithSeparator(String str){
        if(!CharSequenceUtil.endWith(str,File.separator)){
            str = str + File.separator;
        }
        return str;
    }

    /**
     * 字符串是否以斜杠为后缀
     *
     * @param str str
     * @return java.lang.String
     * @author laoyu
     */
    public static  String endWithSlash(String str){
        if(!CharSequenceUtil.endWith(str,SLASH)){
            return str + SLASH;
        }
        return str;
    }



    /**
     * File转 MultipartFile
     *
     * @param file file
     * @return org.springframework.web.multipart.MultipartFile
     * @author laoyu
     */
    public static MultipartFile fileToMultipartFile(File file) {
        FileItem fileItem = createFileItem(file);
        return new CommonsMultipartFile(fileItem);
    }

    /**
     * 创建 FileItem
     *
     * @param file file
     * @return org.apache.commons.fileupload.FileItem
     * @author laoyu
     */
    public static FileItem createFileItem(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("textField", "text/plain", true, file.getName());
        int bytesRead;
        int cacheSize = 8192;
        byte[] buffer = new byte[cacheSize];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, cacheSize)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

}
