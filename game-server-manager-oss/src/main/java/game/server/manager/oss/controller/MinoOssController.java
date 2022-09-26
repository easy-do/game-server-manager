package game.server.manager.oss.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.google.common.collect.Lists;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.enums.OssEnum;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.oss.OssObject;
import game.server.manager.common.result.OssResult;
import game.server.manager.oss.OssUtil;
import game.server.manager.oss.entity.OssManagement;
import game.server.manager.oss.minio.MinioOssStoreServer;
import game.server.manager.oss.service.OssManagementService;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 * @date 2021/11/12
 */
@Log
@RestController
@RequestMapping("/minio")
public class MinoOssController {


    @Autowired
    private MinioOssStoreServer minioOssStoreServer;

    @Autowired
    private OssManagementService ossManagementService;


    /**
     * 单个文件上传
     *
     * @param file      file
     * @param fileName  fileName
     * @param filePath  filePath
     * @param groupName groupName
     * @return game.server.manager.common.result.R<game.server.manager.common.result.OssResult < java.lang.String>>
     * @author laoyu
     * @date 2022/9/25
     */
    @SaCheckLogin
    @PostMapping("/upload")
    public R<OssResult<String>> upload(@RequestParam(name = "file", required = false) MultipartFile file,
                                       @RequestParam(name = "fileName", required = false, defaultValue = "") String fileName,
                                       @RequestParam(name = "filePath", required = false, defaultValue = "") String filePath,
                                       @RequestParam(name = "groupName", required = false, defaultValue = "") String groupName) {
        try {
            OssObject<String, MultipartFile> fileStoreObject = new OssObject<>();
            fileStoreObject.setFileName(fileName);
            fileStoreObject.setGroupName(groupName);
            fileStoreObject.setFilePath(filePath);
            fileStoreObject.setIndex(filePath + fileName);
            fileStoreObject.setFile(file);
            R<OssResult<String>> result = minioOssStoreServer.save(fileStoreObject);
            saveOssLog(result, AuthorizationUtil.getUserId());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.fail("上传失败");
    }


    /**
     * 删除文件
     *
     * @param groupName groupName
     * @param filePath  filePath
     * @param fileName  fileName
     * @return game.server.manager.common.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/9/25
     */
    @SaCheckLogin
    @GetMapping("/remove/{groupName}/{filePath}/{fileName}")
    public R<Object> remove(@PathVariable(name = "groupName") String groupName,
                            @PathVariable(name = "filePath") String filePath,
                            @PathVariable(name = "fileName") String fileName) {
        boolean result = minioOssStoreServer.remove(groupName, OssUtil.endWithSlash(filePath) + fileName);
        if (result) {
            removeOssLog(groupName, filePath, fileName);
            return DataResult.ok();
        } else {
            return DataResult.fail();
        }
    }


    /**
     * 下载文件
     *
     * @param response  response
     * @param groupName groupName
     * @param filePath  filePath
     * @param fileName  fileName
     * @author laoyu
     * @date 2022/9/25
     */
    @GetMapping("/{groupName}/{filePath}/{fileName}")
    public void download(HttpServletResponse response, @PathVariable(name = "groupName") String groupName,
                         @PathVariable(name = "filePath") String filePath,
                         @PathVariable(name = "fileName") String fileName) {
        InputStream in = null;
        try {
            in = minioOssStoreServer.getFile(groupName, OssUtil.endWithSlash(filePath) + fileName);
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
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
     * @param files     files
     * @param groupName groupName
     * @return java.lang.Object
     * @author laoyu
     * @date 2021/11/14
     */
    @PostMapping("/uploads")
    public Object uploadList(@RequestParam("files") List<MultipartFile> files, @RequestParam(name = "groupName", required = false, defaultValue = "") String groupName) {
        List<OssResult<String>> resultList = Lists.newArrayList();
        for (MultipartFile file : files) {
            try {
                minioOssStoreServer.getTemplate().createBucket(groupName);
                OssObject<String, MultipartFile> fileStoreObject = new OssObject<>();
                fileStoreObject.setFile(file);
                fileStoreObject.setGroupName(groupName);
                R<OssResult<String>> result = minioOssStoreServer.save(fileStoreObject);
                resultList.add(result.getData());
            } catch (Exception e) {
                e.printStackTrace();
                return DataResult.fail("文件上传失败");
            }
        }
        return DataResult.ok(resultList);
    }

    @Async
    public void saveOssLog(R<OssResult<String>> r, long userId) {
        if (r.getSuccess()) {
            OssResult<String> ossResult = r.getData();
            OssManagement entity = OssManagement.builder()
                    .groupName(ossResult.getGroupName())
                    .fileName(ossResult.getFileName())
                    .filePath(ossResult.getFilePath())
                    .fileSize(ossResult.getFileSize())
                    .ossType(OssEnum.MINIO.getType())
                    .createBy(userId).createTime(LocalDateTime.now()).build();
            ossManagementService.save(entity);
        }
    }

    @Async
    public void removeOssLog(String groupName, String filePath, String fileName) {
        ossManagementService.removeByWrapper(groupName, filePath, fileName);
    }

}
