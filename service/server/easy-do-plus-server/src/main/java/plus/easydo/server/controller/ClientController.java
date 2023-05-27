package plus.easydo.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.io.IoUtil;
import plus.easydo.api.SysDictDataApi;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;
import plus.easydo.common.vo.ClientInfoVo;
import plus.easydo.server.dto.ClientInfoDto;
import plus.easydo.server.entity.ClientInfo;
import plus.easydo.server.entity.ScriptData;
import plus.easydo.server.service.ClientInfoService;
import plus.easydo.server.service.ScriptDataService;
import plus.easydo.web.base.BaseController;
import plus.easydo.log.SaveLog;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.result.MpDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static plus.easydo.common.constant.Constants.UNIX_LINE_END_CHAR;
import static plus.easydo.common.constant.Constants.WINDOW_LINE_END_CHAR;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/20
 */
@RestController
@RequestMapping("/client")
public class ClientController extends BaseController<ClientInfoService, ClientInfo, String, MpBaseQo<ClientInfo>, ClientInfoVo, ClientInfoDto> {


    public static final String MODULE_NAME = "客户端管理";

    public static final String ADD_DESCRIPTION = "添加客户端: ?1";

    public static final String ADD_EXPRESSIONS = "#p1.applicationName";

    public static final String EDIT_DESCRIPTION = "编辑客户端: ?1 - ?2";

    public static final String REMOVE_DESCRIPTION = "删除客户端: ?1";

    public static final String REMOVE_EXPRESSIONS = "#p1";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private SysDictDataApi sysDictDataService;

    @Autowired
    private ScriptDataService scriptDataService;

    @SaCheckLogin
    @RequestMapping("/list")
    @Override
    public R<List<ClientInfoVo>> list() {
        return super.list();
    }

    @SaCheckLogin
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody MpBaseQo<ClientInfo> mpBaseQo) {
        return super.page(mpBaseQo);
    }

    @SaCheckLogin
    @RequestMapping("/info/{id}")
    @Override
    public R<ClientInfoVo> info(@PathVariable("id")String id) {
        return super.info(id);
    }


    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = "添加应用: ?1",expressions = ADD_EXPRESSIONS,  actionType = ADD_ACTION)
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) ClientInfoDto applicationInfoDto) {
        return super.add(applicationInfoDto);
    }

    @SaCheckLogin
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = EDIT_DESCRIPTION, expressions = {"#p1.applicationId","#p1.applicationName"},  actionType = EDIT_ACTION)
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) ClientInfoDto applicationInfoDto) {
        return super.update(applicationInfoDto);
    }

    @SaCheckLogin
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = REMOVE_ACTION)
    @Override
    public R<Object> remove(@PathVariable("id")String id) {
        return super.remove(id);
    }


    @SaCheckLogin
    @GetMapping("/installCmd/{id}")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = REMOVE_ACTION)
    public R<Object> getInstallCmd(@PathVariable("id")String id) {
        return DataResult.ok(baseService.getInstallCmd(id));
    }

    @GetMapping("/installScript")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = "下载客户端安装脚本", actionType = "下载")
    public void installScript() throws IOException {
        String scriptId = sysDictDataService.getSingleDictData("system_config", "client_install_script").getData().getDictValue();
        ScriptData scriptData = scriptDataService.getById(scriptId);
        byte[] fileBytes = scriptData.getScriptFile().replace(UNIX_LINE_END_CHAR, WINDOW_LINE_END_CHAR).getBytes();
        String mimeType = "application/octet-stream";
        response.setContentType(mimeType);
        response.setContentLength(fileBytes.length);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                "client_install.sh");
        response.setHeader(headerKey, headerValue);
        InputStream inputStream = new ByteArrayInputStream(fileBytes);
        ServletOutputStream opts = response.getOutputStream();
        IoUtil.copy(inputStream, opts);
        response.flushBuffer();
        inputStream.close();
        opts.close();
    }

    @SaCheckLogin
    @GetMapping("/onlineInstall/{id}")
    @SaveLog(logType = "操作日志", moduleName = MODULE_NAME, description = REMOVE_DESCRIPTION, expressions = REMOVE_EXPRESSIONS, actionType = REMOVE_ACTION)
    public R<Object> onlineInstall(@PathVariable("id")String id) {
        return baseService.onlineInstall(id)? DataResult.ok():DataResult.fail();
    }


}
