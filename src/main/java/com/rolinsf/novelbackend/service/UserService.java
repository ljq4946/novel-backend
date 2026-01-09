package com.rolinsf.novelbackend.service;

import com.rolinsf.novelbackend.core.resp.RestResp;
import com.rolinsf.novelbackend.dto.req.UserLoginReqDto;
import com.rolinsf.novelbackend.dto.req.UserRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.UserLoginRespDto;
import com.rolinsf.novelbackend.dto.resp.UserRegisterRespDto;
import jakarta.validation.Valid;

/**
 * @author ljq4946
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param dto 注册参数
     * @return JWT
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto);

    /**
     * 用户登录(用户名，密码)
     *
     * @param dto 登录参数
     * @return JWT + 昵称
     */
    RestResp<UserLoginRespDto> login(@Valid UserLoginReqDto dto);
}
