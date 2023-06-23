package plus.easydo.uc.api;


import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import plus.easydo.uc.dto.AuthorizationCodeDto;
import plus.easydo.uc.vo.AuthorizationCodeVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import plus.easydo.common.dto.AuthorizationConfigDto;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.common.result.R;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/21
 */
@HttpExchange("/authCode")
public interface AuthorizationCodeApi {

    String apiPath = "/authCode";

    @GetExchange(apiPath + "/list")
    public R<List<AuthorizationCodeVo>> list(@RequestParam(required = false) Map<String, Object> queryParam);

    @PostExchange(apiPath + "/page")
    public MpDataResult page(@RequestBody Map<String, Object> queryParam);

    @GetExchange(apiPath + "/info/{id}")
    public R<AuthorizationCodeVo> info(@PathVariable("id") String id);

    @PostExchange(apiPath + "/update")
    public R<Object> edit(@RequestBody AuthorizationCodeDto authorizationCodeDto);

    @PostExchange(apiPath + "/generateAuthCode")
    public R<Object> generateAuthorization(@RequestBody AuthorizationConfigDto authorizationConfigDto);

    @GetExchange(apiPath + "/remove/{id}")
    public R<Object> delete(@PathVariable("id") String id);
}
