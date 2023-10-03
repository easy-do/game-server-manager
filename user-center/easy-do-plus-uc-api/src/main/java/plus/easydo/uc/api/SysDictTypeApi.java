package plus.easydo.uc.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;
import plus.easydo.common.vo.SysDictTypeVo;
import plus.easydo.uc.dto.SysDictTypeDto;

import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 字典类型相关api
 * @date 2023/6/24
 */
@HttpExchange("/oauthClient")
public interface SysDictTypeApi {

    String apiPath = "/oauthClient";
    public MpDataResult page(@RequestBody Map<String,Object> queryParam);

    public R<SysDictTypeVo> info(@PathVariable("id") Long id);

    public R<Object> add(@RequestBody @Validated({Insert.class}) SysDictTypeDto sysDictTypeDto);

    public R<Object> update(@RequestBody @Validated({Update.class}) SysDictTypeDto sysDictTypeDto);

    public R<Object> remove(@PathVariable("id") Long id);

    @PostMapping("/changeStatus")
    public R<Object> changeStatus(@Validated @RequestBody ChangeStatusDto changeStatusDto);
}
