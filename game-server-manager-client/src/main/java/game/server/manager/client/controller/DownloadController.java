package game.server.manager.client.controller;

import cn.hutool.core.io.IoUtil;
import game.server.manager.client.server.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;


    @GetMapping("/{fileName}")
    public void download(@PathVariable("fileName") String fileName) throws IOException {
        String filePath = dictDataService.getValue("plugin_config_file",fileName);
        if(Objects.nonNull(filePath)){
            File file = new File(filePath);
            ServletContext context = request.getServletContext();
            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLength((int) file.length());
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    file.getName());
            response.setHeader(headerKey, headerValue);
            InputStream inputStream = new FileInputStream(filePath);
            ServletOutputStream opts = response.getOutputStream();
            IoUtil.copy(inputStream, opts);
            response.flushBuffer();
            inputStream.close();
            opts.close();
        }
    }




}
