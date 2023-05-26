package plus.easydo.server.config.oss.minio;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import plus.easydo.common.exception.OssException;
import plus.easydo.common.result.OssResult;
import plus.easydo.common.result.R;
import plus.easydo.server.config.oss.OssObject;
import plus.easydo.server.service.oss.AbstractOssService;
import plus.easydo.server.util.oss.OssUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author laoyu
 * @version 1.0
 */
@Service
public class MinioOssStoreServer extends AbstractOssService<String, MultipartFile, InputStream, MinioTemplate> {


    @Override
    public R<OssResult<String>> save(OssObject<String, MultipartFile> ossObject) {
        try {
            R<OssResult<String>> result = super.save(ossObject);
            InputStream in = file.getInputStream();
            String contentType = file.getContentType();
            if (!template.bucketExists(groupName)) {
                template.createBucket(groupName);
            }
            template.putObject(groupName, filePath + SLASH + fileName, in, file.getSize(), contentType);
            OssResult<String> resultFile = result.getData();
            resultFile.setIndex(OssUtil.endWithSlash(groupName) + OssUtil.endWithSlash(filePath) + fileName);
            resultFile.setUrl(OssUtil.endWithSlash(groupName) + OssUtil.endWithSlash(filePath) + fileName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
    }


    @Override
    public InputStream getFile(String groupName, String fileIndex) {
        if (CharSequenceUtil.isBlank(fileIndex)) {
            throw new OssException("fileIndex is empty");
        }
        try {
            return template.getObject(buildGroupName(groupName), fileIndex);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OssException("File does not exist");
        }
    }


    @Override
    public Boolean remove(String groupName, String fileIndex) {
        if (CharSequenceUtil.isBlank(fileIndex)) {
            throw new OssException("fileIndex is empty");
        }
        if (CharSequenceUtil.isBlank(groupName)) {
            try {
                template.removeObject(template.getDefaultBucket(), fileIndex);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                template.removeObject(groupName, fileIndex);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public void copy(String groupName, String index, String targetGroupName, String targetIndex) {
        template.copy(groupName,index,targetGroupName,targetIndex);
    }


    @Override
    public void move(String groupName, String index, String targetGroupName, String targetIndex) {
        template.move(groupName,index,targetGroupName,targetIndex);
    }

    @Override
    public void validationFile(MultipartFile multipartFile) {
        super.validationFile(multipartFile);
        if (multipartFile.getSize() == 0) {
            throw new OssException("fileId is empty");
        }
    }

    @Override
    public String buildFileName(MultipartFile file, String fileName) {
        String prefix = RandomUtil.randomNumbers(10) + "-"+ "minio" + "-";
        if (CharSequenceUtil.isNotBlank(fileName) && !NULL.equals(fileName)) {
            return prefix + fileName;
        }else {
            String originalFilename = file.getOriginalFilename();
            if(CharSequenceUtil.isNotBlank(originalFilename)){
                return prefix + originalFilename;
            }
        }
        return prefix;
    }

    @Override
    public String buildGroupName(String groupName) {
        if (CharSequenceUtil.isBlank(groupName) || NULL.equals(groupName)) {
            groupName = template.getDefaultBucket();
        }
        return groupName;
    }

    @Override
    public BigDecimal getFileKbSize(MultipartFile file) {
        // 首先先将.getSize()获取的Long转为String 然后将String转为Float并除以1024 （因为1KB=1024B）
        float size = Float.parseFloat(String.valueOf(file.getSize())) / 1024;
        // 四舍五入保留2位，
        return BigDecimal.valueOf(size).setScale(2, RoundingMode.HALF_UP);
    }

}
