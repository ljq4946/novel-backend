package com.rolinsf.novelbackend.controller;

import com.rolinsf.novelbackend.core.auth.UserHolder;
import com.rolinsf.novelbackend.core.constant.ApiRouterConst;
import com.rolinsf.novelbackend.core.constant.SystemConfigConst;
import com.rolinsf.novelbackend.core.resp.RestResp;
import com.rolinsf.novelbackend.dto.req.UserInfoUptReqDto;
import com.rolinsf.novelbackend.dto.req.UserLoginReqDto;
import com.rolinsf.novelbackend.dto.req.UserRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.*;
import com.rolinsf.novelbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户模块 API 控制器
 * @author ljq4946
 */
@Tag(name = "UserController", description = "用户模块")
@SecurityRequirement(name = SystemConfigConst.HTTP_AUTH_HEADER_NAME)
@RestController
@RequestMapping(ApiRouterConst.API_FRONT_USER_URL_PREFIX)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    /**
     * 用户注册接口
     */
    @Operation(summary = "用户注册接口")
    @PostMapping("register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto dto) {
        return userService.register(dto);
    }

    /**
     * 用户登录接口(用户名，密码)
     */
    @Operation(summary = "用户登录接口(用户名，密码)")
    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto dto) {
        return userService.login(dto);
    }

    /**
     * 用户信息查询接口
     */
    @Operation(summary = "用户信息查询接口")
    @GetMapping("get")
    public RestResp<UserInfoRespDto> getUserInfo() {
        return userService.getUserInfo(UserHolder.getUserId());
    }

    /**
     * 用户信息修改接口(昵称，头像，性别)
     */
    @Operation(summary = "用户信息修改接口(昵称，头像，性别)")
    @PutMapping("update")
    public RestResp<Void> updateUserInfo(@Valid @RequestBody UserInfoUptReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return userService.updateUserInfo(dto);
    }

    /**
     * 用户签到接口
     */
    @Operation(summary = "用户签到接口")
    @PutMapping("sign")
    public RestResp<UserSignRespDto> sign() {
        return userService.sign(UserHolder.getUserId());
    }

    /**
     * 查询签到信息接口(当月签到记录,连续签到天数)
     */
    @Operation(summary = "查询签到信息接口(当月签到记录,连续签到天数)")
    @GetMapping("sign/get")
    public RestResp<UserSignInfoRespDto> getSignStatistics() {
        return userService.getSignStatistics(UserHolder.getUserId());
    }
}
