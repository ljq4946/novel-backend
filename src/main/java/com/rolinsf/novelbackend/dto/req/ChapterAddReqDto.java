package com.rolinsf.novelbackend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author ljq4946
 */
@Data
public class ChapterAddReqDto {

    /**
     * 小说ID
     */
    @Schema(description = "小说ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bookId;

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