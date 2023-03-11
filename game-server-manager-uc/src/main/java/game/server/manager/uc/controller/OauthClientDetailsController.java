package game.server.manager.uc.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import game.server.manager.web.base.BaseController;
import game.server.manager.uc.qo.OauthClientDetailsQo;
import game.server.manager.uc.service.OauthClientDetailsService;
import game.server.manager.uc.vo.OauthClientDetailsVo;
import game.server.manager.uc.dto.OauthClientDetailsDto;
import  game.server.manager.uc.entity.OauthClientDetails;
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
 * 授权客户端信息Controller
 * 
 * @author yuzhanfeng
 * @date 2023-02-27 16:01:25
 */
@RestController
@RequestMapping("/oauthClient")
public class OauthClientDetailsController extends BaseController<OauthClientDetailsService,OauthClientDetails,String, OauthClientDetailsQo,OauthClientDetailsVo,OauthClientDetailsDto> {

    /**
     * 获取所有授权客户端信息列表
     */
    @SaCheckPermission("oauthClient:list")
    @RequestMapping("/list")
    @Override
    public R<List<OauthClientDetailsVo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询授权客户端信息列表
     */
    @SaCheckPermission("oauthClient:page")
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody OauthClientDetailsQo oauthClientDetailsQo) {
        return super.page(oauthClientDetailsQo);
    }


    /**
     * 获取授权客户端信息详细信息
     */
    @SaCheckPermission("oauthClient:info")
    @GetMapping("/info/{id}")
    @Override
    public R<OauthClientDetailsVo> info(@PathVariable("id")String id) {
        return super.info(id);
    }

    /**
     * 新增授权客户端信息
     */
    @SaCheckPermission("oauthClient:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "授权客户端信息", description = "添加授权客户端信息", actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) OauthClientDetailsDto oauthClientDetailsDto) {
        return super.add(oauthClientDetailsDto);
    }

    /**
     * 修改授权客户端信息
     */
    @SaCheckPermission("oauthClient:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "授权客户端信息", description = "编辑授权客户端信息: ?1", expressions = {"#p1.id"},actionType = "编辑")
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) OauthClientDetailsDto oauthClientDetailsDto) {
        return super.update(oauthClientDetailsDto);
    }

    /**
     * 删除授权客户端信息
     */
    @SaCheckPermission("oauthClient:remove")
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "授权客户端信息", description = "删除授权客户端信息: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")String id) {
        return super.remove(id);
    }
}
