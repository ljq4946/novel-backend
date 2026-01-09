package com.rolinsf.novelbackend.controller;

import com.rolinsf.novelbackend.core.constant.ApiRouterConst;
import com.rolinsf.novelbackend.core.constant.SystemConfigConst;
import com.rolinsf.novelbackend.core.resp.RestResp;
import com.rolinsf.novelbackend.dto.req.UserRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.UserRegisterRespDto;
import com.rolinsf.novelbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块 API 控制器
 * @author ljq4946
 */
@Tag(name = "UserController", description = "前台门户-会员模块")
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

}
