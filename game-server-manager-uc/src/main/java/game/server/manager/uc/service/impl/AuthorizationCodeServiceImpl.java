package game.server.manager.uc.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.common.dto.AuthorizationConfigDto;
import game.server.manager.uc.entity.AuthorizationCode;
import game.server.manager.uc.service.AuthorizationCodeService;
import game.server.manager.uc.mapper.AuthorizationCodeMapper;
import game.server.manager.common.vo.UserInfoVo;
import org.springframework.stereotype.Service;
import game.server.manager.common.exception.BizException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author yuzhanfeng
* @description 针对表【authorization_code(授权码)】的数据库操作Service实现
* @createDate 2022-05-19 13:13:29
*/
@Service
public class AuthorizationCodeServiceImpl extends ServiceImpl<AuthorizationCodeMapper, AuthorizationCode>
    implements AuthorizationCodeService {

//    @Autowired
//    private ServerInfoService serverInfoService;
//
//    @Autowired
//    private ApplicationInfoService applicationInfoService;
//
//    @Autowired
//    private AppInfoService appInfoService;
//
//    @Autowired
//    private AppScriptService appScriptService;

    @Override
    public boolean generateAuthorization(AuthorizationConfigDto authorizationConfigDto) {
        List<AuthorizationCode> authorizationCodeList = new ArrayList<>();
        for (int i = 0; i < authorizationConfigDto.getGenNum(); i++) {
            String code = UUID.fastUUID().toString(false);
            AuthorizationCode authorizationCode = AuthorizationCode.builder().code(code).config(JSON.toJSONString(authorizationConfigDto)).build();
            authorizationCode.setDescription(authorizationConfigDto.getDescription());
            authorizationCode.setState(authorizationConfigDto.getState());
            authorizationCodeList.add(authorizationCode);
        }
        return saveBatch(authorizationCodeList);
    }

    @Override
    public void checkAuthorization(UserInfoVo user, String type) {
        String userAuth = user.getAuthorization();
        AuthorizationConfigDto authorizationConfigDto = JSON.parseObject(userAuth, AuthorizationConfigDto.class);
        if(CharSequenceUtil.isEmpty(userAuth)) {
            throw new BizException("授权信息不存在");
        }
        Long id = authorizationConfigDto.getId();
        AuthorizationCode authorizationCode = baseMapper.selectById(id);
        if(Objects.isNull(authorizationCode)){
            throw new BizException("授权信息不存在");
        }
        String configStr = authorizationCode.getConfig();
        AuthorizationConfigDto config = JSON.parseObject(configStr, AuthorizationConfigDto.class);
        if(LocalDateTimeUtil.between(LocalDateTime.now(),config.getExpires()).toMillis() < 0L){
            throw new BizException("授权到期自动清除授权信息");
        }
        Long userId = user.getId();
//        switch (type) {
//            case "serverAdd" -> {
//                long serverCount = serverInfoService.countByUserId(userId);
//                if (serverCount >= config.getServerNum()) {
//                    throw new BizException("已到达授权服务数量限制");
//                }
//
//            }
//            case "applicationAdd" -> {
//                long applicationCount = applicationInfoService.countByUserId(userId);
//                if (applicationCount >= config.getApplicationNum()) {
//                    throw new BizException("已到达授权应用数量限制");
//                }
//            }
//            case "appAdd" -> {
//                long appCount = appInfoService.countByUserId(userId);
//                if (appCount >= config.getAppCreationNum()) {
//                    throw new BizException("已到达授权应用创作数量限制");
//                }
//            }
//            case "appScriptAdd" -> {
//                long appCount = appScriptService.countByUserId(userId);
//                if (appCount >= config.getScriptCreationNum()) {
//                    throw new BizException("已到达授权脚本创作数量限制");
//                }
//            }
//            default -> {
//                break;
//            }
//        }
    }
}




