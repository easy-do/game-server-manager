package plus.easydo.api;

import plus.easydo.common.result.OssResult;
import plus.easydo.common.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yuzhanfeng
 */
public interface OssApi {

    /**
     * 上传
     *
     * @param file file
     * @param fileName fileName
     * @param filePath filePath
     * @param groupName groupName
     * @return result.plus.easydo.common.R<result.plus.easydo.common.OssResult<java.lang.String>>
     * @author laoyu
     * @date 2022/9/27
     */
    @PostMapping("/upload")
    public R<OssResult<String>> upload(@RequestParam(name = "file", required = false) MultipartFile file,
                                       @RequestParam(name = "fileName", required = false, defaultValue = "") String fileName,
                                       @RequestParam(name = "filePath", required = false, defaultValue = "") String filePath,
                                       @RequestParam(name = "groupName", required = false, defaultValue = "")String groupName);

    /**
     * 删除
     *
     * @param response response
     * @param groupName groupName
     * @param filePath filePath
     * @param fileName fileName
     * @return result.plus.easydo.common.R<java.lang.Object>
     * @author laoyu
     * @date 2022/9/27
     */
    @GetMapping("/remove/{groupName}/{filePath}/{fileName}")
    public R<Object> remove(HttpServletResponse response, @PathVariable(name = "groupName") String groupName,
                            @PathVariable(name = "filePath") String filePath,
                            @PathVariable(name = "fileName") String fileName);


    /**
     * 下载
     *
     * @param response response
     * @param groupName groupName
     * @param filePath filePath
     * @param fileName fileName
     * @author laoyu
     * @date 2022/9/27
     */
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
