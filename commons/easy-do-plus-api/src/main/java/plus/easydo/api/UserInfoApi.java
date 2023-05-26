package plus.easydo.api;

import plus.easydo.common.result.R;
import plus.easydo.common.vo.UserInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/31
 */

public interface UserInfoApi {

    /**
     * 获取用户详情
     *
     * @param userId userId
     * @return result.plus.easydo.common.R<vo.plus.easydo.common.UserInfoVo>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/getUserInfo")
    R<UserInfoVo> getUserInfo(@RequestParam("userId") Long userId);


    /**
     * 获取用户数量
     *
     * @return result.plus.easydo.common.R<vo.plus.easydo.common.UserInfoVo>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/count")
    R<Long> count();

    /**
     * 获得用户头像
     *
     * @param id id
     * @return result.plus.easydo.common.R<java.lang.String>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/avatar/{id}")
    R<String> avatar(@PathVariable("id")Long id);

}
