package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.web.base.BaseController;
import game.server.manager.common.exception.BizException;
import game.server.manager.server.entity.FileStore;
import game.server.manager.log.SaveLog;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import game.server.manager.server.service.FileStoreService;
import game.server.manager.common.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 文件存储
 * @date 2022/7/14
 */
@RestController
@RequestMapping("/fileStore")
public class FileStoreController {

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private HttpServletResponse response;

    @SaCheckRole("super_admin")
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<FileStore> wrapper = Wrappers.lambdaQuery();
        wrapper.select(FileStore::getId,FileStore::getFileName,FileStore::getFileSize,FileStore::getCreateTime);
        return MpResultUtil.buildPage(fileStoreService.page(mpBaseQo.startPage()));
    }

    @SaCheckRole("super_admin")
    @GetMapping("/info/{id}")
    public R<FileStore> info(@PathVariable("id") Long id) {
        return DataResult.ok(fileStoreService.getById(id));
    }

    @SaCheckRole("super_admin")
    @GetMapping("/delete/{id}")
    public R<FileStore> delete(@PathVariable("id") Long id) {
        return fileStoreService.removeById(id)?DataResult.ok():DataResult.fail();
    }

    @SaCheckLogin()
    @SaveLog(logType = BaseController.LOG_TYPE, moduleName = "文件存储", description = "上传文件.", expressions = {""}, actionType = "文件上传")
    @PostMapping("/upload")
    public R<String> download(MultipartFile file) throws IOException {
        if(Objects.isNull(file)){
            return DataResult.fail("未获取到文件");
        }
        UserInfoVo user = AuthorizationUtil.getUser();
        int maxFileNameLength = 32;
        int maxFileSize = 2048 * 1024;
        long fileSize = file.getSize();
        //校验文件大小
        if(!user.isAdmin() && fileSize > maxFileSize){
            throw new BizException("文件大小不得超过"+(maxFileSize/1024)+"kb。");
        }
        //根据md5查找是否存在相同文件，存在则直接返回已有的文件
        String md5 = getMd5(file);
        FileStore fileStore = fileStoreService.getByMd5(md5);
        if(Objects.nonNull(fileStore)){
            return DataResult.ok(String.valueOf(fileStore.getId()));
        }
        //保存文件
        InputStream inStream = file.getInputStream();
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int size = 100;
        byte[] buff = new byte[size];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, size)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] fileByte = swapStream.toByteArray();
        String fileName = file.getOriginalFilename();
        if(CharSequenceUtil.isEmpty(fileName) || fileName.length() > maxFileNameLength){
        String suffix = CharSequenceUtil.subAfter(file.getOriginalFilename(), ".", false);
        fileName = DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_MS_PATTERN) +"." + suffix;
        }
        FileStore entity = FileStore.builder().fileName(fileName).md5(md5)
                .file(fileByte).fileSize(fileSize/1024).createTime(LocalDateTime.now()).build();
        fileStoreService.save(entity);
        return DataResult.ok(String.valueOf(entity.getId()));
    }

    @GetMapping("/dl/{id}")
    public void download(@PathVariable("id") Long id) throws IOException {
        FileStore fileStore = fileStoreService.getById(id);
        byte[] file = fileStore.getFile();
        String mimeType = "application/octet-stream";
        response.setContentType(mimeType);
        response.setContentLength(file.length);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                fileStore.getFileName());
        response.setHeader(headerKey, headerValue);
        InputStream inputStream = new ByteArrayInputStream(file);
        IoUtil.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    @GetMapping("/base64/{id}")
    public String base64(@PathVariable("id") Long id) {
        FileStore fileStore = fileStoreService.getById(id);
        byte[] file = fileStore.getFile();
        return Base64Utils.encodeToString(file);
    }


    /**
     * 获取上传文件的md5
     */
    public String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            throw new BizException(ExceptionUtil.getMessage(e));
        }
    }



}
