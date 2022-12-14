package game.server.manager.uc.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.common.exception.HasPermissionException;
import game.server.manager.common.dto.AuthorizationConfigDto;
import game.server.manager.event.BasePublishEventServer;
import game.server.manager.uc.dto.ResetPasswordDto;
import game.server.manager.uc.entity.AuthorizationCode;
import game.server.manager.uc.entity.UserInfo;
import game.server.manager.common.enums.AuthCodeStateEnum;
import game.server.manager.common.exception.AuthorizationException;
import game.server.manager.uc.mapper.UserInfoMapper;
import game.server.manager.uc.mapstruct.UserInfoMapstruct;
import game.server.manager.uc.service.AuthorizationCodeService;
import game.server.manager.uc.service.EmailService;
import game.server.manager.uc.service.SysRoleService;
import game.server.manager.uc.service.UserInfoService;
import game.server.manager.uc.dto.RestPasswordModel;
import game.server.manager.common.vo.UserInfoVo;
import me.zhyd.oauth.model.AuthUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import game.server.manager.common.exception.BizException;

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoVo getOrCreateUserInfo(AuthUser authUser) {
        String unionId = authUser.getUuid();
        String platform = authUser.getSource();
        UserInfo userInfo;
        userInfo = getUserInfoByUnionIdAndPlatform(unionId, platform);
        if (Objects.nonNull(userInfo)) {
            if (userInfo.getState() == 1) {
                throw new HasPermissionException("???????????????");
            }
            return UserInfoMapstruct.INSTANCE.entityToVo(userInfo);
        } else {
            return createUser(authUser);
        }
    }


    /**
     * ??????unionid?????????????????????????????????
     *
     * @param unionId  unionId
     * @param platform platform
     * @return game.server.manager.server.entity.UserInfo
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
        //????????????
        String source = authUser.getSource();
        String nickname = authUser.getNickname();
        String unionId = authUser.getUuid();
        String avatar = authUser.getAvatar();
        if (CharSequenceUtil.isEmpty(nickname)) {
            //????????????????????????????????????????????????
            nickname = "??????" + DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_MS_PATTERN);
        }
        UserInfo userInfo = UserInfo.builder().unionId(unionId).nickName(nickname).avatarUrl(avatar).platform(source).secret(UUID.fastUUID().toString(true))
                .state(0).createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).build();
        if (save(userInfo)) {
            //??????????????????
            sysRoleService.bindingDefaultRole(userInfo.getId());
            //????????????????????????
            basePublishEventServer.publishRegisterEvent(userInfo.getId());
            return UserInfoMapstruct.INSTANCE.entityToVo(userInfo);
        } else {
            throw new AuthorizationException("????????????");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean authorization(UserInfoVo userInfoVo, String authorizationCode) {
        boolean result;
        LambdaQueryWrapper<AuthorizationCode> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AuthorizationCode::getCode, authorizationCode);
        AuthorizationCode authorizationCodeEntity = authorizationCodeService.getOne(wrapper);
        if (Objects.nonNull(authorizationCodeEntity)) {
            if (authorizationCodeEntity.getState() == 1) {
                throw new AuthorizationException("?????????????????????");
            }
            UserInfo userEntity = baseMapper.selectById(userInfoVo.getId());
            if (Objects.isNull(userEntity)) {
                throw new HasPermissionException("???????????????");
            }
            AuthorizationConfigDto authorizationConfigDto = JSON.parseObject(authorizationCodeEntity.getConfig(), AuthorizationConfigDto.class);
            authorizationConfigDto.setId(authorizationCodeEntity.getId());
            UserInfoVo newUserInfoVo = UserInfoMapstruct.INSTANCE.entityToVo(userEntity);
            //????????????????????????????????????????????????
            if (authorizationCodeEntity.getState() == AuthCodeStateEnum.REUSABLE.getState()) {
                result = setUserAuthorization(authorizationConfigDto, userEntity);
                if (result) {
                    newUserInfoVo.setAuthorization(JSON.toJSONString(authorizationConfigDto));
                    AuthorizationUtil.reloadUserCache(newUserInfoVo);
                    basePublishEventServer.publishAuthorizationCodeEvent(userEntity.getId(),authorizationConfigDto);
                }
                return result;
            }
            //??????????????????????????????
            if (setUserAuthorization(authorizationConfigDto, userEntity)) {
                newUserInfoVo.setAuthorization(JSON.toJSONString(authorizationConfigDto));
                authorizationCodeEntity.setState(1);
                result = authorizationCodeService.updateById(authorizationCodeEntity);
                if(result){
                    AuthorizationUtil.reloadUserCache(newUserInfoVo);
                    basePublishEventServer.publishAuthorizationCodeEvent(userEntity.getId(),authorizationConfigDto);
                }
                return result;
            }
        } else {
            throw new AuthorizationException("??????????????????");
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
            throw new BizException("500", "??????????????????????????????");
        }
        boolean result = emailService.checkCode(email, code);
        if (result) {
            UserInfo user = baseMapper.selectById(id);
            user.setEmail(email);
            boolean updateResult = updateById(user);
            if (updateResult) {
                AuthorizationUtil.reloadUserCache(UserInfoMapstruct.INSTANCE.entityToVo(user));
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
            throw new BizException("500", "??????????????????");
        }
        boolean result = emailService.checkCode(userInfo.getEmail(), restPasswordModel.getEmailCode());
        if (result) {
            //?????????
            String salt = BCrypt.gensalt();
            //??????
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
            throw new BizException("500", "??????????????????");
        }
        //?????????
        String salt = BCrypt.gensalt();
        //??????
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
        UserInfoVo cacheUser = AuthorizationUtil.getUser();
        String secret = UUID.fastUUID().toString(true);
        UserInfo entity = UserInfo.builder().id(cacheUser.getId()).secret(secret).build();
        boolean result = updateById(entity);
        if (result) {
            cacheUser.setSecret(secret);
            AuthorizationUtil.reloadUserCache(cacheUser);
            basePublishEventServer.publishResetSecretEvent(cacheUser.getId());
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

}




