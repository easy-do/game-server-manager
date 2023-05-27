package plus.easydo.generate.api;


import org.springframework.validation.annotation.Validated;
import plus.easydo.generate.vo.DataSourceVo;
import plus.easydo.generate.dto.DataSourceDto;
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
 * 数据源管理Api
 *
 * @author yuzhanfeng
 * @date 2023-05-27 17:51:43
 */
public interface DataSourceManagerApi {

    String apiPath = "/dataSource";

    /**
     * 获取所有数据源管理列表
     */
    @GetMapping(apiPath + "/list")
    public R<List<DataSourceVo>> list(@RequestParam(required = false) Map<String, Object> queryParam);

    /**
     * 分页条件查询数据源管理列表
     */
    @PostMapping(apiPath + "/page")
    public MpDataResult page(@RequestBody Map<String, Object> queryParam);


    /**
     * 获取数据源管理详细信息
     */
    @GetMapping(apiPath + "/info/{id}")
    public R<DataSourceVo> info(@PathVariable("id")Long id);

    /**
     * 新增数据源管理
     */
    @PostMapping(apiPath + "/add")
    public R<Object> add(@RequestBody @Validated({Insert.class}) DataSourceDto dataSourceManagerDto);

    /**
     * 修改数据源管理
     */
    @PostMapping(apiPath + "/update")
    public R<Object> update(@RequestBody @Validated({Update.class}) DataSourceDto dataSourceManagerDto);

    /**
     * 删除数据源管理
     */
    @GetMapping(apiPath + "/remove/{id}")
    public R<Object> remove(@PathVariable("id")Long id);
}
