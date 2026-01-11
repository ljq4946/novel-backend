package com.rolinsf.novelbackend.service;

import com.rolinsf.novelbackend.dto.resp.RestResp;
import com.rolinsf.novelbackend.dto.req.UserInfoUptReqDto;
import com.rolinsf.novelbackend.dto.req.UserLoginReqDto;
import com.rolinsf.novelbackend.dto.req.UserRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.*;
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
     * @param dto 登录参数
     * @return JWT + 昵称
     */
    RestResp<UserLoginRespDto> login(@Valid UserLoginReqDto dto);

    /**
     * 用户信息查询
     * @param userId 用户ID
     * @return 用户信息
     */
    RestResp<UserInfoRespDto> getUserInfo(Long userId);

    /**
     * 用户信息修改
     * @param dto 用户信息
     * @return void
     */
    RestResp<Void> updateUserInfo(UserInfoUptReqDto dto);

    /**
     * 用户签到
     * @param userId 用户ID
     */
    RestResp<UserSignRespDto> sign(Long userId);

    /**
     * 查询签到统计信息(当月签到记录,连续签到天数)
     * @param userId 用户ID
     * @return 当月签到记录,连续签到天数
     */
    RestResp<UserSignInfoRespDto> getSignStatistics(Long userId);

    /**
     * 查询书架状态接口
     * @param userId 用户ID
     * @param bookId 小说ID
     * @return 0-不在书架 1-已在书架
     */
    RestResp<Integer> getBookshelfStatus(Long userId, String bookId);
}
