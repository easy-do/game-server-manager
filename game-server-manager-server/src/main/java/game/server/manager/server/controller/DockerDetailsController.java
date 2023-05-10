package game.server.manager.server.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import game.server.manager.server.dto.DockerDetailsDto;
import game.server.manager.server.vo.server.DockerDetailsVo;
import game.server.manager.web.base.BaseController;
import game.server.manager.server.qo.server.DockerDetailsQo;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.log.SaveLog;
import game.server.manager.mybatis.plus.result.MpDataResult;
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
 * docker配置信息Controller
 * 
 * @author yuzhanfeng
 * @date 2022-11-13 12:10:30
 */
@RestController
@RequestMapping("/dockerDetails")
public class DockerDetailsController extends BaseController<DockerDetailsService, DockerDetails,Long, DockerDetailsQo, DockerDetailsVo, DockerDetailsDto> {

    /**
     * 获取所有docker配置信息列表
     */
    @SaCheckPermission("manager:DockerDetails:list")
    @RequestMapping("/list")
    @Override
    public R<List<DockerDetailsVo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询docker配置信息列表
     */
    @SaCheckPermission("manager:DockerDetails:page")
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody DockerDetailsQo dockerDetailsQo) {
        return super.page(dockerDetailsQo);
    }


    /**
     * 获取docker配置信息详细信息
     */
    @SaCheckPermission("manager:DockerDetails:info")
    @GetMapping("/info/{id}")
    @Override
    public R<DockerDetailsVo> info(@PathVariable("id")Long id) {
        return super.info(id);
    }

    /**
     * 新增docker配置信息
     */
    @SaCheckPermission("manager:DockerDetails:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "docker配置信息", description = "添加docker配置信息", actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) DockerDetailsDto dockerDetailsDto) {
        return super.add(dockerDetailsDto);
    }

    /**
     * 修改docker配置信息
     */
    @SaCheckPermission("manager:DockerDetails:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "docker配置信息", description = "编辑docker配置信息: ?1", expressions = {"#p1.id"},actionType = "编辑")
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) DockerDetailsDto dockerDetailsDto) {
        return super.update(dockerDetailsDto);
    }

    /**
     * 删除docker配置信息
     */
    @SaCheckPermission("manager:DockerDetails:remove")
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "docker配置信息", description = "删除docker配置信息: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }
}
