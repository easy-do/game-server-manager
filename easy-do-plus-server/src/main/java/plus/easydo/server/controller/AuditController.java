package plus.easydo.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.server.dto.AuditDto;
import plus.easydo.log.SaveLog;
import plus.easydo.server.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/23
 */
@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @SaCheckRole(SystemConstant.SUPER_ADMIN_ROLE)
    @PostMapping("/audit")
    @SaveLog(logType = "操作日志", moduleName = "审核模块", description = "审核: ?1,?2,?3 ", expressions = {"#p1.id","#p1.auditType","#p1.status"}, actionType = "审核")
    public R<Object> commit(@RequestBody AuditDto auditDto) {
        return auditService.audit(auditDto)? DataResult.ok():DataResult.fail();
    }
    @SaCheckLogin()
    @PostMapping("/commitAudit")
    @SaveLog(logType = "操作日志", moduleName = "审核模块", description = "提交审核: ?1 , ?2 ", expressions = {"#p1.id","#p1.auditType"}, actionType = "提交审核")
    public R<Object> commitAudit(@RequestBody AuditDto auditDto) {
        return auditService.commitAudit(auditDto)? DataResult.ok():DataResult.fail();
    }

}
