package com.rolinsf.novelbackend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录 请求DTO
 * @author ljq4946
 */
@Data
public class UserLoginReqDto {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="用户名不能为空！")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="密码不能为空！")
    private String password;

}