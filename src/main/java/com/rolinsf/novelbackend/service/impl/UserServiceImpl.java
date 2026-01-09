package com.rolinsf.novelbackend.service.impl;

import com.rolinsf.novelbackend.core.constant.ErrorCodeEnum;
import com.rolinsf.novelbackend.core.constant.SystemConfigConst;
import com.rolinsf.novelbackend.core.exception.BusinessException;
import com.rolinsf.novelbackend.core.resp.RestResp;
import com.rolinsf.novelbackend.core.util.JwtUtils;
import com.rolinsf.novelbackend.dao.entity.UserInfo;
import com.rolinsf.novelbackend.dao.mapper.UserInfoMapper;
import com.rolinsf.novelbackend.dto.req.UserRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.UserRegisterRespDto;
import com.rolinsf.novelbackend.manager.VerifyCodeManager;
import com.rolinsf.novelbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author ljq4946
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userInfoMapper;

    private final VerifyCodeManager verifyCodeManager;

    private final JwtUtils jwtUtils;
    @Override
    public RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto) {
        // 校验图形验证码是否正确
        if (!verifyCodeManager.imgVerifyCodeOk(dto.getSessionId(), dto.getVelCode())) {
            // 图形验证码校验失败
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }

        // 保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword(
                DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8)));
        userInfo.setUsername(dto.getUsername());
        userInfo.setNickName("默认昵称");
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setSalt("0");
        userInfoMapper.insert(userInfo);

        // 删除验证码
        verifyCodeManager.removeImgVerifyCode(dto.getSessionId());

        // 生成JWT 并返回
        return RestResp.ok(
                UserRegisterRespDto.builder()
                        .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConst.NOVEL_FRONT_KEY))
                        .uid(userInfo.getId())
                        .build()
        );
    }
}
