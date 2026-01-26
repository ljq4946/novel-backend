package com.rolinsf.novelbackend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ljq4946
 */
@Data

public class AuthorEmaCodeReqDto {

    @Schema(hidden = true)
    private Long userId;
    /**
     * 电子邮箱
     */
    @Schema(description = "电子邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "电子邮箱不能为空！")
    @Email(message = "邮箱格式不正确！")
    private String email;
}
