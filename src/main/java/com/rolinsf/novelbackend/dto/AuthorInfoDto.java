package com.rolinsf.novelbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ljq4946
 */
@Data
@Builder
public class AuthorInfoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String penName;

    private Integer status;

}
