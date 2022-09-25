package game.server.manager.oss.controller;

import com.google.common.collect.Lists;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.oss.OssObject;
import game.server.manager.common.result.OssResult;
import game.server.manager.oss.OssUtil;
import game.server.manager.oss.local.LocalOssService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2021/11/26
 */
@RestController
@RequestMapping("/local")
public class LocalOssController {

    @Autowired
    LocalOssService localOssService;

    @ResponseBody
    @PostMapping("/upload")
    public R<Object> upload(@RequestParam(name = "file", required = false) MultipartFile file,
                            @RequestParam(name = "fileName", required = false, defaultValue = "") String fileName,
                            @RequestParam(name = "filePath", required = false, defaultValue = "") String filePath,
                            @RequestParam(name = "groupName", required = false, defaultValue = "")String groupName){
        try {
            OssObject<String,MultipartFile> OssObject = new OssObject<>();
            OssObject.setFileName(fileName);
            OssObject.setFilePath(filePath);
            OssObject.setGroupName(groupName);
            OssObject.setIndex(fileName);
            OssObject.setFile(file);
            R<OssResult<String>> result = localOssService.save(OssObject);
            return DataResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.fail("上传失败");
    }

    @GetMapping("/{groupName}/{filePath}/{fileName}")
    public void download(HttpServletResponse response, @PathVariable(name = "groupName") String groupName,
                         @PathVariable(name = "filePath") String filePath,
                         @PathVariable(name = "fileName") String fileName) {
        InputStream in=null;
        try {
            filePath = OssUtil.endWithSlash(filePath);
            in = localOssService.getFile(groupName,filePath + fileName);
            IOUtils.copy(in,response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 多上传文件
     *
     * @param files files
     * @param groupName groupName
     * @return java.lang.Object
     * @author laoyu
     * @date 2021/11/14
     */
    @PostMapping("/list")
    public Object uploadList(@RequestParam("files") List<MultipartFile> files, @RequestParam(name = "groupName", required = false, defaultValue = "") String groupName) {
        List<OssResult<String>> resultList = Lists.newArrayList();
        for (MultipartFile file : files) {
            try {
                OssObject<String,MultipartFile> OssObject = new OssObject<>();
                OssObject.setFile(file);
                OssObject.setGroupName(groupName);
                R<OssResult<String>> result = localOssService.save(OssObject);
                resultList.add(result.getData());
            } catch (Exception e) {
                e.printStackTrace();
                return DataResult.fail("文件上传失败");
            }
        }
        return DataResult.ok(resultList);
    }


}
