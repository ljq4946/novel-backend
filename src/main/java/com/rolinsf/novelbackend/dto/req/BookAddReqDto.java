package com.rolinsf.novelbackend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author ljq4946
 */
@Data
public class BookAddReqDto {
    /**
     * 类别ID
     */
    @Schema(description = "类别ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long categoryId;

    /**
     * 类别名
     */
    @Schema(description = "类别名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String categoryName;

    /**
     * 小说封面地址
     */
    @Schema(description = "小说封面地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String picUrl;

    /**
     * 小说名
     */
    @Schema(description = "小说名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String bookName;

    /**
     * 书籍描述
     */
    @Schema(description = "书籍描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String bookDesc;

}
