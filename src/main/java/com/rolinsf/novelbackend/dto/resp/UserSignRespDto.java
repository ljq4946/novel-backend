package com.rolinsf.novelbackend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ljq4946
 */
@Data
@Builder
public class UserSignRespDto {
    /**
     * 签到时间
     * */
    @Schema(description = "签到时间")
    private LocalDateTime signTime;

    /**
     * 连续签到天数
     */
    @Schema(description = "累计签到天数")
    private Integer continuousSignDays;
}
