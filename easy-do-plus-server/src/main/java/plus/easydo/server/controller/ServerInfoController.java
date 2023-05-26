package plus.easydo.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import plus.easydo.server.dto.ServerInfoDto;
import plus.easydo.server.entity.ServerInfo;
import plus.easydo.server.service.ServerInfoService;
import plus.easydo.web.base.BaseController;
import plus.easydo.log.SaveLog;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.common.vo.ServerInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/19
 */
@RestController
@RequestMapping("/serverInfo")
public class ServerInfoController extends BaseController<ServerInfoService, ServerInfo,Long,MpBaseQo<ServerInfo>,ServerInfoVo, ServerInfoDto> {

    @SaCheckLogin
    @RequestMapping("/list")
    @Override
    public R<List<ServerInfoVo>> list() {
        return super.list();
    }

    @SaCheckLogin
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody MpBaseQo<ServerInfo> mpBaseQo) {
        return super.page(mpBaseQo);
    }

    @SaCheckLogin
    @RequestMapping("/info/{id}")
    @Override
    public R<ServerInfoVo> info(@PathVariable("id")Long id) {
return super.info(id);
    }

    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "服务管理", description = "添加服务器: ?1", expressions = {"#p1.serverName"}, actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) ServerInfoDto serverInfoDto) {
        baseService.test(serverInfoDto);
        return super.add(serverInfoDto);
    }

    @SaCheckLogin
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "服务管理", description = "编辑服务器: ?1 - ?2", expressions = {"#p1.id","#p1.serverName"},actionType = "编辑")
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) ServerInfoDto serverInfoDto) {
        baseService.test(serverInfoDto);
        return super.update(serverInfoDto);
    }

    @SaCheckLogin
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "服务管理", description = "删除服务器: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }

}
