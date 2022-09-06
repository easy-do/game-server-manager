package game.server.manager.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import game.server.manager.web.base.BaseController;
import game.server.manager.common.result.DataResult;
import game.server.manager.server.dto.ServerInfoDto;
import game.server.manager.server.entity.ServerInfo;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.server.service.ServerInfoService;
import game.server.manager.common.vo.ServerInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import game.server.manager.common.result.R;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/19
 */
@RestController
@RequestMapping("/serverInfo")
public class ServerInfoController extends BaseController<ServerInfoService,ServerInfo,Long,MpBaseQo<ServerInfo>,ServerInfoVo,ServerInfoDto> {

    @SaCheckLogin
    @RequestMapping("/list")
    public R<List<ServerInfoVo>> list() {
        return super.list();
    }

    @SaCheckLogin
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo<ServerInfo> mpBaseQo) {
        return super.page(mpBaseQo);
    }

    @SaCheckLogin
    @RequestMapping("/info/{id}")
    public R<ServerInfoVo> info(@PathVariable("id")Long id) {
return super.info(id);
    }

    @SaCheckLogin
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "服务管理", description = "添加服务器: ?1", expressions = {"#p1.serverName"}, actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) ServerInfoDto serverInfoDto) {
        return super.add(serverInfoDto);
    }

    @SaCheckLogin
    @PostMapping("/edit")
    @SaveLog(logType = "操作日志", moduleName = "服务管理", description = "编辑服务器: ?1 - ?2", expressions = {"#p1.id","#p1.serverName"},actionType = "编辑")
    public R<Object> edit(@RequestBody @Validated({Update.class}) ServerInfoDto serverInfoDto) {
        return super.edit(serverInfoDto);
    }

    @SaCheckLogin
    @GetMapping("/delete/{id}")
    @SaveLog(logType = "操作日志", moduleName = "服务管理", description = "删除服务器: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> delete(@PathVariable("id")Long id) {
        return super.delete(id);
    }

    @SaCheckLogin
    @PostMapping("/test")
    @SaveLog(logType = "操作日志", moduleName = "服务管理", description = "测试服务器连通性", expressions = {}, actionType = "测试")
    public R<Object> test(@RequestBody @Validated({Insert.class}) ServerInfoDto serverInfoDto) {
        baseService.test(serverInfoDto);
        return DataResult.ok();
    }
}
