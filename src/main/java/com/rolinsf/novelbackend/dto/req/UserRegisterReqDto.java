package com.rolinsf.novelbackend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author ljq4946
 */
@Data
public class UserRegisterReqDto {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="用户名不能为空！")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="密码不能为空！")
    private String password;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message="验证码不能为空！")
    @Pattern(regexp="^\\d{4}$",message="验证码格式不正确！")
    private String velCode;

    /**
     * 请求会话标识，用来标识图形验证码属于哪个会话
     * */
    @Schema(description = "sessionId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Length(min = 32,max = 32)
    private String sessionId;
}