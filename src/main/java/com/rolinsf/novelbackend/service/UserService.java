package com.rolinsf.novelbackend.service;

import com.rolinsf.novelbackend.core.resp.RestResp;
import com.rolinsf.novelbackend.dto.req.UserRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.UserRegisterRespDto;

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
}
