package game.server.manager.api.feign.fallback;

import game.server.manager.api.feign.OssClient;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.OssResult;
import game.server.manager.common.result.R;
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
