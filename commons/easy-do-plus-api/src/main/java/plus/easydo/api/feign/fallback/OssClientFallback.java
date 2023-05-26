package plus.easydo.api.feign.fallback;

import plus.easydo.api.feign.OssClient;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.OssResult;
import plus.easydo.common.result.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */
public class OssClientFallback implements OssClient {


    @Override
    public R<OssResult<String>> upload(MultipartFile file, String fileName, String filePath, String groupName) {
        return DataResult.fail();
    }

    @Override
    public R<Object> remove(HttpServletResponse response, String groupName, String filePath, String fileName) {
        return DataResult.fail();
    }

    @Override
    public void download(HttpServletResponse response, String groupName, String filePath, String fileName) {

    }

    @Override
    public Object uploadList(List<MultipartFile> files, String groupName) {
        return DataResult.fail();
    }
}
