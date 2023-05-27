package plus.easydo.generate.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import lombok.RequiredArgsConstructor;
import plus.easydo.common.result.DataResult;
import  plus.easydo.dao.result.MpResultUtil;
import plus.easydo.generate.api.DataSourceManagerApi;
import plus.easydo.generate.entity.dataSourceManager;
import plus.easydo.generate.service.DataSourceManagerService;
import plus.easydo.generate.vo.DataSourceVo;
import plus.easydo.generate.dto.DataSourceDto;
import plus.easydo.log.SaveLog;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.generate.mapstruct.DataSourceMapstruct;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.util.Map;


/**
 * 数据源管理Controller
 *
 * @author yuzhanfeng
 * @date 2023-05-27 17:53:09
 */
@RestController
@RequestMapping(DataSourceManagerApi.apiPath)
@RequiredArgsConstructor
public class DataSourceManagerController implements DataSourceManagerApi{


    private final DataSourceManagerService baseService;

    private final BeanSearcher beanSearcher;

    private final DataSourceMapstruct convert;


    /**
     * 获取所有数据源管理列表
     */
    @SaCheckPermission("generate:dataSource:list")
    @GetMapping("/list")
    public R<List<DataSourceVo>> list(@RequestParam(required = false) Map<String, Object> queryParam) {
        List<dataSourceManager> result = beanSearcher.searchList(dataSourceManager.class, queryParam);
        return DataResult.ok(convert.entityToVo(result));
    }

    /**
     * 分页条件查询数据源管理列表
     */
    @SaCheckPermission("generate:dataSource:page")
    @PostMapping("/page")
    public MpDataResult page(@RequestBody Map<String,Object> queryParam) {
        SearchResult<dataSourceManager> result = beanSearcher.search(dataSourceManager.class, queryParam);
        List<DataSourceVo> voList = convert.entityToVo(result.getDataList());
        return MpResultUtil.buildPage(voList,(Long)result.getTotalCount());
    }


    /**
     * 获取数据源管理详细信息
     */
    @SaCheckPermission("generate:dataSource:info")
    @GetMapping("/info/{id}")
    public R<DataSourceVo> info(@PathVariable("id")Long id) {
        dataSourceManager entity = baseService.getById(id);
        return DataResult.ok(convert.entityToVo(entity));
    }

    /**
     * 新增数据源管理
     */
    @SaCheckPermission("generate:dataSource:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "数据源管理", description = "添加数据源管理", actionType = "添加")
    public R<Object> add(@RequestBody @Validated({Insert.class}) DataSourceDto dataSourceManagerDto) {
        dataSourceManager entity = convert.dtoToEntity(dataSourceManagerDto);
        return baseService.save(entity)? DataResult.ok():DataResult.fail();
    }

    /**
     * 修改数据源管理
     */
    @SaCheckPermission("generate:dataSource:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "数据源管理", description = "编辑数据源管理: ?1", expressions = {"#p1.id"},actionType = "编辑")
    public R<Object> update(@RequestBody @Validated({Update.class}) DataSourceDto dataSourceManagerDto) {
        dataSourceManager entity = convert.dtoToEntity(dataSourceManagerDto);
        return baseService.updateById(entity)? DataResult.ok():DataResult.fail();
    }

    /**
     * 删除数据源管理
     */
    @SaCheckPermission("generate:dataSource:remove")
    @GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "数据源管理", description = "删除数据源管理: ?1", expressions = {"#p1"}, actionType = "删除")
    public R<Object> remove(@PathVariable("id")Long id) {
        return baseService.removeById(id)? DataResult.ok():DataResult.fail();
    }

    /**
     * 测试数据源
     *
     * @param dto dto
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @PostMapping("/test")
    public R<Object> test(@RequestBody @Validated DataSourceDto dto) {
        return baseService.test(dto);
    }
}
