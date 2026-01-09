package com.rolinsf.novelbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rolinsf.novelbackend.core.constant.DatabaseConst;
import com.rolinsf.novelbackend.core.constant.ErrorCodeEnum;
import com.rolinsf.novelbackend.core.constant.SystemConfigConst;
import com.rolinsf.novelbackend.core.exception.BusinessException;
import com.rolinsf.novelbackend.core.resp.RestResp;
import com.rolinsf.novelbackend.core.util.JwtUtils;
import com.rolinsf.novelbackend.dao.entity.UserInfo;
import com.rolinsf.novelbackend.dao.mapper.UserInfoMapper;
import com.rolinsf.novelbackend.dto.req.UserLoginReqDto;
import com.rolinsf.novelbackend.dto.req.UserRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.UserLoginRespDto;
import com.rolinsf.novelbackend.dto.resp.UserRegisterRespDto;
import com.rolinsf.novelbackend.manager.VerifyCodeManager;
import com.rolinsf.novelbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public RestResp<UserLoginRespDto> login(UserLoginReqDto dto) {
        // 查询用户信息
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.UserInfoTable.COLUMN_USERNAME, dto.getUsername())
                .last(DatabaseConst.SqlEnum.LIMIT_1.getSql());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (Objects.isNull(userInfo)) {
            // 用户不存在
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }

        // 判断密码是否正确
        if (!Objects.equals(userInfo.getPassword()
                , DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8)))) {
            // 密码错误
            throw new BusinessException(ErrorCodeEnum.USER_PASSWORD_ERROR);
        }

        // 登录成功，生成JWT并返回
        return RestResp.ok(UserLoginRespDto.builder()
                .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConst.NOVEL_FRONT_KEY))
                .uid(userInfo.getId())
                .nickName(userInfo.getNickName()).build());
    }
}
