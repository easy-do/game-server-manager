package game.server.manager.oss.local;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import game.server.manager.common.result.R;
import game.server.manager.oss.OssResult;
import game.server.manager.oss.OssObject;
import game.server.manager.oss.OssUtil;
import game.server.manager.oss.exception.OssStoreException;
import game.server.manager.oss.service.AbstractOssService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 本地文件存储实现
 *
 * @author laoyu
 * @version 1.0
 */
@Component
public class LocalOssService extends AbstractOssService<String, MultipartFile, InputStream, LocalOssTemplate> {

    @Override
    public R<OssResult<String>> save(OssObject<String, MultipartFile> ossObject) {
        R<OssResult<String>> result = super.save(ossObject);
        template.save(groupName,filePath,fileName,file);
        OssResult<String> resultFile = result.getData();
        resultFile.setIndex(OssUtil.endWithSlash(groupName) + OssUtil.endWithSlash(filePath) + fileName);
        resultFile.setUrl(OssUtil.endWithSlash(groupName) + OssUtil.endWithSlash(filePath) + fileName);
        result.setData(resultFile);
        return result;
    }

    @Override
    public InputStream getFile(String groupName, String fileIndex) {
        if (CharSequenceUtil.isBlank(fileIndex)) {
            throw new OssStoreException("fileId is empty");
        }
        return template.getFile(groupName, fileIndex);
    }

    @Override
    public Boolean remove(String groupName, String fileIndex) {
        return template.remove(groupName, fileIndex);
    }

    @Override
    public void copy(String groupName, String index, String targetGroupName, String targetIndex) {
         template.copy(groupName, index, targetGroupName, targetIndex);

    }

    @Override
    public void move(String groupName, String index, String targetGroupName, String targetIndex) {
        template.move(groupName, index, targetGroupName, targetIndex);
    }

    @Override
    public String buildFileName(MultipartFile file, String fileName) {
        String prefix = RandomUtil.randomNumbers(10) + "-"+ "local" + "-";
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
            groupName = template.getDefaultGroupName();
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
