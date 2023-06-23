package plus.easydo.uc.api;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.service.annotation.HttpExchange;
import plus.easydo.uc.vo.OauthClientDetailsVo;
import plus.easydo.uc.dto.OauthClientDetailsDto;
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
 * 授权客户端信息Api
 *
 * @author yuzhanfeng
 * @date 2023-05-27 02:18:43
 */
@HttpExchange("/oauthClient")
public interface OauthClientDetailsApi {

    String apiPath = "/oauthClient";

    /**
     * 获取所有授权客户端信息列表
     */
    @GetMapping(apiPath + "/list")
    public R<List<OauthClientDetailsVo>> list(@RequestParam(required = false) Map<String, Object> queryParam);

    /**
     * 分页条件查询授权客户端信息列表
     */
    @PostMapping(apiPath + "/page")
    public MpDataResult page(@RequestBody Map<String, Object> queryParam);


    /**
     * 获取授权客户端信息详细信息
     */
    @GetMapping(apiPath + "/info/{id}")
    public R<OauthClientDetailsVo> info(@PathVariable("id")Long id);

    /**
     * 新增授权客户端信息
     */
    @PostMapping(apiPath + "/add")
    public R<Object> add(@RequestBody @Validated({Insert.class}) OauthClientDetailsDto oauthClientDetailsDto);

    /**
     * 修改授权客户端信息
     */
    @PostMapping(apiPath + "/update")
    public R<Object> update(@RequestBody @Validated({Update.class}) OauthClientDetailsDto oauthClientDetailsDto);

    /**
     * 删除授权客户端信息
     */
    @GetMapping(apiPath + "/remove/{id}")
    public R<Object> remove(@PathVariable("id")Long id);
}
