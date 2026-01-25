package com.rolinsf.novelbackend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author ljq4946
 */
@Data
public class ChapterUpdateReqDto {

    /**
     * 章节名
     */
    @NotBlank
    @Schema(description = "章节名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String chapterName;

    /**
     * 章节内容
     */
    @Schema(description = "章节内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Length(min = 50)
    private String chapterContent;



}