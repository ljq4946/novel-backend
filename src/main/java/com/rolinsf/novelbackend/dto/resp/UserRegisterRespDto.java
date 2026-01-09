package com.rolinsf.novelbackend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author ljq4946
 */
@Data
@Builder
public class UserRegisterRespDto {

    @Schema(description = "用户ID")
    private Long uid;

    @Schema(description = "用户token")
    private String token;
}