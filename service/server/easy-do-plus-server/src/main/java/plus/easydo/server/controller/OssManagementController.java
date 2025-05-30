package plus.easydo.server.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;
import plus.easydo.log.SaveLog;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.server.dto.OssManagementDto;
import plus.easydo.server.entity.OssManagement;
import plus.easydo.server.qo.oss.OssManagementQo;
import plus.easydo.server.service.oss.OssManagementService;
import plus.easydo.server.vo.oss.OssManagementVo;
import plus.easydo.web.base.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 存储管理Controller
 * 
 * @author yuzhanfeng
 * @date 2022-09-26 16:35:09
 */
@RestController
@RequestMapping("/ossManagement")
public class OssManagementController extends BaseController<OssManagementService, OssManagement,Long, OssManagementQo, OssManagementVo, OssManagementDto> {

    /**
     * 获取所有存储管理列表
     */
    @SaCheckPermission("system:oss:ossManagement:list")
    @RequestMapping("/list")
    @Override
    public R<List<OssManagementVo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询存储管理列表
     */
    @SaCheckPermission("system:oss:ossManagement:page")
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody OssManagementQo ossManagementQo) {
        return super.page(ossManagementQo);
    }


    /**
     * 获取存储管理详细信息
     */
    @SaCheckPermission("system:oss:ossManagement:info")
    @GetMapping("/info/{id}")
    @Override
    public R<OssManagementVo> info(@PathVariable("id")Long id) {
        return super.info(id);
    }

    /**
     * 新增存储管理
     */
    @SaCheckPermission("system:oss:ossManagement:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "存储管理", description = "添加存储管理", actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) OssManagementDto ossManagementDto) {
        return super.add(ossManagementDto);
    }

    /**
     * 修改存储管理
     */
    @SaCheckPermission("system:oss:ossManagement:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "存储管理", description = "编辑存储管理: ?1", expressions = {"#p1.id"},actionType = "编辑")
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) OssManagementDto ossManagementDto) {
        return super.update(ossManagementDto);
    }

    /**
     * 删除存储管理
     */
    @SaCheckPermission("system:oss:ossManagement:remove")
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "存储管理", description = "删除存储管理: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }
}
