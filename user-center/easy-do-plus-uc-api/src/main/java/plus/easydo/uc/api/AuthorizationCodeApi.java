package plus.easydo.uc.api;


import plus.easydo.uc.dto.AuthorizationCodeDto;
import plus.easydo.uc.vo.AuthorizationCodeVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public interface AuthorizationCodeApi {

    String apiPath = "/authCode";

    @GetMapping(apiPath + "/list")
    public R<List<AuthorizationCodeVo>> list(@RequestParam(required = false) Map<String, Object> queryParam);

    @PostMapping(apiPath + "/page")
    public MpDataResult page(@RequestBody Map<String, Object> queryParam);

    @GetMapping(apiPath + "/info/{id}")
    public R<AuthorizationCodeVo> info(@PathVariable("id") String id);

    @PostMapping(apiPath + "/update")
    public R<Object> edit(@RequestBody AuthorizationCodeDto authorizationCodeDto);

    @PostMapping(apiPath + "/generateAuthCode")
    public R<Object> generateAuthorization(@RequestBody AuthorizationConfigDto authorizationConfigDto);

    @GetMapping(apiPath + "/remove/{id}")
    public R<Object> delete(@PathVariable("id") String id);
}
