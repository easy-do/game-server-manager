package plus.easydo.generate.api;


import org.springframework.validation.annotation.Validated;
import plus.easydo.generate.vo.TemplateManagementVo;
import plus.easydo.generate.dto.TemplateManagementDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import java.util.List;
import java.util.Map;


/**
 * 模板管理Api
 *
 * @author yuzhanfeng
 * @date 2023-05-27 17:57:02
 */
public interface TemplateManagementApi {

    String apiPath = "/template";

    /**
     * 获取所有模板管理列表
     */
    @GetMapping(apiPath + "/list")
    public R<List<TemplateManagementVo>> list(@RequestParam(required = false) Map<String, Object> queryParam);

    /**
     * 分页条件查询模板管理列表
     */
    @PostMapping(apiPath + "/page")
    public MpDataResult page(@RequestBody Map<String, Object> queryParam);


    /**
     * 获取模板管理详细信息
     */
    @GetMapping(apiPath + "/info/{id}")
    public R<TemplateManagementVo> info(@PathVariable("id")Long id);

    /**
     * 新增模板管理
     */
    @PostMapping(apiPath + "/add")
    public R<Object> add(@RequestBody @Validated({Insert.class}) TemplateManagementDto templateManagementDto);

    /**
     * 修改模板管理
     */
    @PostMapping(apiPath + "/update")
    public R<Object> update(@RequestBody @Validated({Update.class}) TemplateManagementDto templateManagementDto);

    /**
     * 删除模板管理
     */
    @GetMapping(apiPath + "/remove/{id}")
    public R<Object> remove(@PathVariable("id")Long id);
}
