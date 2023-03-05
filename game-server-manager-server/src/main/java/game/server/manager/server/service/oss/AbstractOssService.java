package game.server.manager.server.service.oss;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import game.server.manager.common.exception.OssException;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.OssResult;
import game.server.manager.common.result.R;
import game.server.manager.server.config.oss.OssObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 文件存储服务的抽象实现类，实现共有方法
 * @date 2022/9/24
 */

public abstract class AbstractOssService <INDEX, INPUT_FILE, RESULT_FILE, TEMPLATE> extends DataResult<Object> implements OssStoreService<INDEX, INPUT_FILE, RESULT_FILE, TEMPLATE>{

    protected static final String SLASH = "/";
    protected static final String NULL = "null";

    protected OssObject<INDEX,INPUT_FILE> ossObject;
    protected INPUT_FILE file;
    protected String filePath;
    protected String fileName;
    protected String groupName;
    protected BigDecimal fileKbSize;

    @Autowired
    protected TEMPLATE template;

    @Override
    public TEMPLATE getTemplate() {
        return this.template;
    }

    @Override
    public R<OssResult<INDEX>> save(OssObject<INDEX, INPUT_FILE> ossObject) {
        validationFile(ossObject.getFile());
        this.ossObject = ossObject;
        this.file = ossObject.getFile();
        this.fileName = buildFileName(ossObject.getFile(), ossObject.getFileName());
        this.filePath = buildFilePath(ossObject.getFilePath());
        this.groupName = buildGroupName(ossObject.getGroupName());
        this.fileKbSize = getFileKbSize(file);
        return ok(buildResult());
    }

    @Override
    public void validationFile(INPUT_FILE inputFile) {
        if(Objects.isNull(inputFile)){
            throw new OssException("file is empty");
        }

    }

    @Override
    public String buildFilePath(String filePath) {
        if (CharSequenceUtil.isBlank(filePath) || NULL.equals(filePath)){
            filePath = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.PURE_DATE_PATTERN);
        }else if (filePath.endsWith(SLASH)){
            filePath = CharSequenceUtil.subAfter(filePath,SLASH,false);
        }
        return filePath;
    }

    @Override
    public OssResult<INDEX> buildResult() {
        OssResult<INDEX> result = new OssResult<>();
        result.setGroupName(groupName);
        result.setFileName(fileName);
        result.setFilePath(filePath);
        result.setFileSize(fileKbSize);
        return result;
    }
}
