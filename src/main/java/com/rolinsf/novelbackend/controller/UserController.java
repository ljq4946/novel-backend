package com.rolinsf.novelbackend.controller;

import com.rolinsf.novelbackend.core.auth.UserHolder;
import com.rolinsf.novelbackend.core.constant.ApiRouterConst;
import com.rolinsf.novelbackend.core.constant.SystemConfigConst;
import com.rolinsf.novelbackend.dto.req.*;
import com.rolinsf.novelbackend.dto.resp.RestResp;
import com.rolinsf.novelbackend.dto.resp.*;
import com.rolinsf.novelbackend.service.BookService;
import com.rolinsf.novelbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final BookService bookService;
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

    /**
     * 发表评论接口
     */
    @Operation(summary = "发表评论接口")
    @PostMapping("comment")
    public RestResp<Void> comment(@Valid @RequestBody UserCommentReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return bookService.saveComment(dto);
    }

    /**
     * 删除评论接口
     */
    @Operation(summary = "删除评论接口")
    @DeleteMapping("comment/{id}")
    public RestResp<Void> deleteComment(@Parameter(description = "评论ID") @PathVariable Long id) {
        return bookService.deleteComment(UserHolder.getUserId(), id);
    }

    /**
     * 分页查询评论
     */
    @Operation(summary = "查询评论列表接口")
    @GetMapping("comments")
    public RestResp<PageRespDto<UserCommentRespDto>> listComments(PageReqDto pageReqDto) {
        return bookService.listComments(UserHolder.getUserId(), pageReqDto);
    }

    /**
     * 查询书架状态接口 0-不在书架 1-已在书架
     */
    @Operation(summary = "查询书架状态接口")
    @GetMapping("bookshelf_status")
    public RestResp<Integer> getBookshelfStatus(@Parameter(description = "小说ID") String bookId) {
        return userService.getBookshelfStatus(UserHolder.getUserId(), bookId);
    }

}
