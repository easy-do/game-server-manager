package plus.easydo.uc.service.impl;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.common.constant.SystemConstant;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.common.exception.HasPermissionException;
import plus.easydo.common.dto.AuthorizationConfigDto;
import plus.easydo.event.BasePublishEventServer;
import plus.easydo.uc.dto.ResetPasswordDto;
import plus.easydo.uc.entity.AuthorizationCode;
import plus.easydo.uc.entity.UserInfo;
import plus.easydo.common.enums.AuthCodeStateEnum;
import plus.easydo.common.exception.AuthorizationException;
import plus.easydo.uc.mapper.UserInfoMapper;
import plus.easydo.uc.mapstruct.UserInfoMapstruct;
import plus.easydo.uc.service.AuthorizationCodeService;
import plus.easydo.uc.service.EmailService;
import plus.easydo.uc.service.SysRoleService;
import plus.easydo.uc.service.UserInfoService;
import plus.easydo.uc.dto.RestPasswordModel;
import plus.easydo.common.vo.UserInfoVo;
import me.zhyd.oauth.model.AuthUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.easydo.common.exception.BizException;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author yuzhanfeng
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private AuthorizationCodeService authorizationCodeService;


    @Autowired
    private EmailService emailService;


    @Autowired
    private BasePublishEventServer basePublishEventServer;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private AuthorizationUtil authorizationUtil;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoVo getOrCreateUserInfo(AuthUser authUser) {
        String unionId = authUser.getUuid();
        String platform = authUser.getSource();
        UserInfo userInfo;
        userInfo = getUserInfoByUnionIdAndPlatform(unionId, platform);
        if (Objects.nonNull(userInfo)) {
            if (userInfo.getState() == 1) {
                throw new HasPermissionException("账号黑名单");
            }
            return UserInfoMapstruct.INSTANCE.entityToVo(userInfo);
        } else {
            return createUser(authUser);
        }
    }


    /**
     * 根据unionid和平台类型获取用户信息
     *
     * @param unionId  unionId
     * @param platform platform
     * @return plus.easydo.push.server.entity.UserInfo
     * @author laoyu
     * @date 2022/6/12
     */
    private UserInfo getUserInfoByUnionIdAndPlatform(String unionId, String platform) {
        LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getUnionId, unionId);
        wrapper.eq(UserInfo::getPlatform, platform);
        return baseMapper.selectOne(wrapper);
    }

    public UserInfoVo createUser(AuthUser authUser) {
        //创建用户
        String source = authUser.getSource();
        String nickname = authUser.getNickname();
        String unionId = authUser.getUuid();
        String avatar = authUser.getAvatar();
        if (CharSequenceUtil.isEmpty(nickname)) {
            //如果没有同步到昵称则设置一个昵称
            nickname = "用户" + DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_MS_PATTERN);
        }
        UserInfo userInfo = UserInfo.builder().unionId(unionId).nickName(nickname).avatarUrl(avatar).platform(source).secret(UUID.fastUUID().toString(true))
                .state(0).createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).build();
        if (save(userInfo)) {
            //关联默认角色
            sysRoleService.bindingDefaultRole(userInfo.getId());
            //发布注册成功事件
            basePublishEventServer.publishRegisterEvent(userInfo.getId());
            return UserInfoMapstruct.INSTANCE.entityToVo(userInfo);
        } else {
            throw new AuthorizationException("注册失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean authorization(String authorizationCode) {
        boolean result;
        LambdaQueryWrapper<AuthorizationCode> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AuthorizationCode::getCode, authorizationCode);
        AuthorizationCode authorizationCodeEntity = authorizationCodeService.getOne(wrapper);
        if (Objects.nonNull(authorizationCodeEntity)) {
            if (authorizationCodeEntity.getState() == 1) {
                throw new AuthorizationException("授权码已被使用");
            }
            UserInfo userEntity = baseMapper.selectById(AuthorizationUtil.getSimpleUser().getId());
            if (Objects.isNull(userEntity)) {
                throw new HasPermissionException("用户不存在");
            }
            AuthorizationConfigDto authorizationConfigDto = JSON.parseObject(authorizationCodeEntity.getConfig(), AuthorizationConfigDto.class);
            authorizationConfigDto.setId(authorizationCodeEntity.getId());
            UserInfoVo newUserInfoVo = UserInfoMapstruct.INSTANCE.entityToVo(userEntity);
            //可重复使用的授权码直接为角色授权
            if (authorizationCodeEntity.getState() == AuthCodeStateEnum.REUSABLE.getState()) {
                result = setUserAuthorization(authorizationConfigDto, userEntity);
                if (result) {
                    newUserInfoVo.setAuthorization(JSON.toJSONString(authorizationConfigDto));
                    authorizationUtil.reloadUserCache(newUserInfoVo);
                    basePublishEventServer.publishAuthorizationCodeEvent(userEntity.getId(),authorizationConfigDto);
                }
                return result;
            }
            //正常授权并消费授权码
            if (setUserAuthorization(authorizationConfigDto, userEntity)) {
                newUserInfoVo.setAuthorization(JSON.toJSONString(authorizationConfigDto));
                authorizationCodeEntity.setState(1);
                result = authorizationCodeService.updateById(authorizationCodeEntity);
                if(result){
                    authorizationUtil.reloadUserCache(newUserInfoVo);
                    basePublishEventServer.publishAuthorizationCodeEvent(userEntity.getId(),authorizationConfigDto);
                }
                return result;
            }
        } else {
            throw new AuthorizationException("授权码不存在");
        }
        return false;
    }

    @NotNull
    private boolean setUserAuthorization(AuthorizationConfigDto authorizationConfigDto, UserInfo userEntity) {
        userEntity.setAuthorization(JSON.toJSONString(authorizationConfigDto));
        return baseMapper.updateById(userEntity) > 0;
    }

    @Override
    public void setBlack(Long id) {
        UserInfo entity = UserInfo.builder().id(id).state(1).build();
        baseMapper.updateById(entity);
    }

    @Override
    public boolean bindingEmail(Long id, String email, String code) {
        LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getEmail, email);
        if (baseMapper.selectCount(wrapper) > 0) {
            throw new BizException("500", "邮箱已绑定其他账号。");
        }
        boolean result = emailService.checkCode(email, code);
        if (result) {
            UserInfo user = baseMapper.selectById(id);
            user.setEmail(email);
            boolean updateResult = updateById(user);
            if (updateResult) {
                authorizationUtil.reloadUserCache(UserInfoMapstruct.INSTANCE.entityToVo(user));
                basePublishEventServer.publishBindingEmailEvent(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean resetPassword(RestPasswordModel restPasswordModel) {
        Long userId = restPasswordModel.getUserId();
        UserInfo userInfo = baseMapper.selectById(userId);
        if (Objects.isNull(userInfo)) {
            throw new BizException("500", "用户不存在。");
        }
        boolean result = emailService.checkCode(userInfo.getEmail(), restPasswordModel.getEmailCode());
        if (result) {
            //生成盐
            String salt = BCrypt.gensalt();
            //加密
            String encryptPass = BCrypt.hashpw(restPasswordModel.getPassword(),salt);
            userInfo.setSalt(salt);
            userInfo.setPassword(encryptPass);
            boolean updateResult = updateById(userInfo);
            if(updateResult){
                basePublishEventServer.publishResetPasswordEvent(userId,restPasswordModel.getPassword());
            }
            return updateResult;
        }
        return false;
    }

    @Override
    public boolean resetPassword(ResetPasswordDto resetPasswordDto) {
        Long userId = resetPasswordDto.getUserId();
        String password = resetPasswordDto.getPassword();
        Long updateUser = resetPasswordDto.getUpdateUserId();
        UserInfo userInfo = baseMapper.selectById(userId);
        if (Objects.isNull(userInfo)) {
            throw new BizException("500", "用户不存在。");
        }
        //生成盐
        String salt = BCrypt.gensalt();
        //加密
        String encryptPass = BCrypt.hashpw(password,salt);
        userInfo.setSalt(salt);
        userInfo.setPassword(encryptPass);
        userInfo.setUpdateBy(updateUser);
        boolean updateResult = updateById(userInfo);
        if(updateResult){
            basePublishEventServer.publishResetPasswordEvent(userId,password);
        }
        return updateResult;
    }

    @Override
    public boolean resetSecret() {
        UserInfoVo cacheUser = authorizationUtil.getUser();
        String secret = UUID.fastUUID().toString(true);
        UserInfo entity = UserInfo.builder().id(cacheUser.getId()).secret(secret).build();
        boolean result = updateById(entity);
        if (result) {
            cacheUser.setSecret(secret);
            authorizationUtil.reloadUserCache(cacheUser);
            basePublishEventServer.publishResetSecretEvent(cacheUser.getId());
            StpUtil.login(cacheUser.getId(), SaLoginConfig.setExtra(SystemConstant.TOKEN_USER_INFO, UserInfoMapstruct.INSTANCE.voToSimpleVo(cacheUser)));
        }
        return result;
    }

    @Override
    public String avatar(Long id) {
        LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getId, id);
        wrapper.select(UserInfo::getAvatarUrl);
        UserInfo user = baseMapper.selectOne(wrapper);
        if(Objects.nonNull(user)){
            if(CharSequenceUtil.isNotEmpty(user.getAvatarUrl())){
                return user.getAvatarUrl();
            }
        }
        return "";
    }

    @Override
    public boolean updateUserStatus(ChangeStatusDto changeStatusDto) {
        Long userId = changeStatusDto.getId();
        Integer status = changeStatusDto.getStatus();
        Long updateUserId = changeStatusDto.getUpdateUserId();
        UserInfo entity = UserInfo.builder().id(userId).state(status).updateBy(updateUserId).build();
        return updateById(entity);
    }

    @Override
    public boolean userResetPassword(RestPasswordModel restPasswordModel) {
        long userId = AuthorizationUtil.getUserId();
        UserInfo entity = UserInfo.builder().id(userId).build();
        //生成盐
        String salt = BCrypt.gensalt();
        //加密
        String encryptPass = BCrypt.hashpw(restPasswordModel.getPassword(),salt);
        entity.setSalt(salt);
        entity.setPassword(encryptPass);
        entity.setUpdateBy(userId);
        return updateById(entity);
    }

}




