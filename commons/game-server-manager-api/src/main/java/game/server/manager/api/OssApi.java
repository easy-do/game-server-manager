package game.server.manager.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.OssResult;
import game.server.manager.common.result.R;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author yuzhanfeng
 */
public interface OssApi {

    @SaCheckLogin
    @PostMapping("/upload")
    public R<OssResult<String>> upload(@RequestParam(name = "file", required = false) MultipartFile file,
                                       @RequestParam(name = "fileName", required = false, defaultValue = "") String fileName,
                                       @RequestParam(name = "filePath", required = false, defaultValue = "") String filePath,
                                       @RequestParam(name = "groupName", required = false, defaultValue = "")String groupName);

    @SaCheckLogin
    @GetMapping("/remove/{groupName}/{filePath}/{fileName}")
    public R<Object> remove(HttpServletResponse response, @PathVariable(name = "groupName") String groupName,
                            @PathVariable(name = "filePath") String filePath,
                            @PathVariable(name = "fileName") String fileName);



    @GetMapping("/{groupName}/{filePath}/{fileName}")
    public void download(HttpServletResponse response, @PathVariable(name = "groupName") String groupName,
                         @PathVariable(name = "filePath") String filePath,
                         @PathVariable(name = "fileName") String fileName);

    /**
     * 多上传文件
     *
     * @param files files
     * @param groupName groupName
     * @return java.lang.Object
     * @author laoyu
     * @date 2021/11/14
     */
    @PostMapping("/uploads")
    public Object uploadList(@RequestParam("files") List<MultipartFile> files, @RequestParam(name = "groupName", required = false, defaultValue = "") String groupName);

}
