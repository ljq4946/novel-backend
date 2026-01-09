package com.rolinsf.novelbackend.controller;

import com.rolinsf.novelbackend.core.constant.ApiRouterConst;
import com.rolinsf.novelbackend.core.resp.RestResp;
import com.rolinsf.novelbackend.dto.resp.ImgVerifyCodeRespDto;
import com.rolinsf.novelbackend.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 前台门户-资源(图片/视频/文档)模块 API 控制器
 *
 * @author ljq4946
 */
@Tag(name = "ResourceController", description = "前台门户-资源模块")
@RestController
@RequestMapping(ApiRouterConst.API_FRONT_RESOURCE_URL_PREFIX)
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    /**
     * 获取图片验证码接口
     */
    @Operation(summary = "获取图片验证码接口")
    @GetMapping("img_verify_code")
    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
        return resourceService.getImgVerifyCode();
    }
}
