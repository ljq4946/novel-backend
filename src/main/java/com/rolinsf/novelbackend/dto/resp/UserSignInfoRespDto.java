package com.rolinsf.novelbackend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author ljq4946
 */
@Data
@Builder
public class UserSignInfoRespDto {

    @Schema(description = "连续签到天数")
    private Integer continuousSignDays;

    @Schema(description = "当月签到情况")
    private List<Boolean> monthSignData;

}
