package game.server.manager.api;

import game.server.manager.common.result.R;
import game.server.manager.common.vo.UserInfoVo;
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
     * @return game.server.manager.common.result.R<game.server.manager.common.vo.UserInfoVo>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/getUserInfo")
    R<UserInfoVo> getUserInfo(@RequestParam("userId") Long userId);


    /**
     * 获取用户数量
     *
     * @return game.server.manager.common.result.R<game.server.manager.common.vo.UserInfoVo>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/count")
    R<Long> count();

    /**
     * 获得用户头像
     *
     * @param id id
     * @return game.server.manager.common.result.R<java.lang.String>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/avatar/{id}")
    R<String> avatar(@PathVariable("id")Long id);

}
