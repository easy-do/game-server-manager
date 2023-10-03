package plus.easydo.uc.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;
import plus.easydo.common.vo.SysDictDataVo;
import plus.easydo.uc.dto.SysDictDataDto;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 字典数据相关api
 * @date 2023/6/24
 */
@HttpExchange("/dictData")
public interface SysDictDataApi {

    String apiPath = "/dictData";

    @GetExchange("/list")
    public R<List<SysDictDataVo>> list(@RequestParam(required = false) Map<String, Object> queryParam);

    @PostExchange("/page")
    public MpDataResult page(@RequestBody Map<String,Object> queryParam);

    @GetExchange("/info/{id}")
    public R<SysDictDataVo> info(@PathVariable("id") Long id);

    @PostExchange("/add")
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysDictDataDto sysDictDataDto);

    @PostExchange("/update")
    public R<Object> update(@RequestBody @Validated({Update.class}) SysDictDataDto sysDictDataDto);

    @GetExchange("/remove/{id}")
    public R<Object> remove(@PathVariable("id") Long id);

    @PostExchange("/changeStatus")
    public R<Object> changeStatus(@Validated @RequestBody ChangeStatusDto changeStatusDto);

    @GetExchange("/listByCode/{dictCode}")
    public R<Object> listByCode(@PathVariable("dictCode") String dictCode);

    @GetExchange("/getSingleDictData/{dictCode}/{dictDataKey}")
    public R<SysDictDataVo> getSingleDictData(@PathVariable("dictCode")String dictCode, @PathVariable("dictDataKey")String dictDataKey);

    @GetExchange("/getDictDataMap")
    public R<Map<String, List<SysDictDataVo>>> getDictDataMap();

}
