package com.rolinsf.novelbackend.service;

import com.rolinsf.novelbackend.dto.req.AuthorRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.RestResp;

/**
 * @author ljq4946
 */
public interface AuthorService {
    /**
     * 作家注册
     *
     * @param dto 注册参数
     * @return void
     */
    RestResp<Void> register(AuthorRegisterReqDto dto);

    /**
     * 查询作家状态
     *
     * @param userId 用户ID
     * @return 作家状态
     */
    RestResp<Integer> getStatus(Long userId);
}
