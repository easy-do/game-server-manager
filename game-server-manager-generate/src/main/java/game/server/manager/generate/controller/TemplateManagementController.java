package game.server.manager.generate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.generate.dto.TemplateManagementDto;
import game.server.manager.generate.qo.TemplateManagementQo;
import game.server.manager.generate.service.TemplateManagementService;
import game.server.manager.generate.vo.TemplateManagementVo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模板管理Controller
 *
 * @author gebilaoyu
 */
@RestController
@RequestMapping("/template")
public class TemplateManagementController {

    @Autowired
    private TemplateManagementService templateManagementService;

    /**
     * 分页条件查询模板管理列表
     *
     * @param qo qo
     * @return plus.easydo.starter.mybatis.plus.R.MPDataR
     * @author laoyu
     */
    @PostMapping("/page")
    public MpDataResult page(@RequestBody TemplateManagementQo qo) {
        IPage<TemplateManagementVo> page = templateManagementService.page(qo);
        return MpResultUtil.buildPage(page);
    }

    /**
     * 获取所有模板管理列表
     *
     * @param qo qo
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @PostMapping("/list")
    public R<List<TemplateManagementVo>> list(@RequestBody TemplateManagementQo qo) {
        return DataResult.ok(templateManagementService.list(qo));
    }


    /**
     * 获取模板管理详细信息
     *
     * @param id id
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @GetMapping(value = "/info/{id}")
    public R<Object> getInfo(@PathVariable("id") Long id) {
        return DataResult.ok(templateManagementService.selectById(id));
    }

    /**
     * 新增模板管理
     *
     * @param dto dto
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @PostMapping("/add")
    public R<Object> add(@RequestBody @Validated TemplateManagementDto dto) {
        return DataResult.ok(templateManagementService.insert(dto));
    }

    /**
     * 修改模板管理
     *
     * @param dto dto
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @PostMapping("/update")
    public R<Object> edit(@RequestBody @Validated TemplateManagementDto dto) {
        return templateManagementService.update(dto)?DataResult.ok():DataResult.fail();
    }

    /**
     * 删除模板管理
     *
     * @param id id
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @GetMapping("/remove/{id}")
    public R<Object> remove(@PathVariable Long id) {
        return DataResult.ok(templateManagementService.deleteById(id));
    }
}
