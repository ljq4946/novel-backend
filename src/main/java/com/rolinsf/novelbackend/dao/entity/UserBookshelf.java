package com.rolinsf.novelbackend.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户书架
 * </p>
 *
 * @author ljq4946
 * @since 2026-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_bookshelf")
public class UserBookshelf implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 小说ID
     */
    private Long bookId;

    /**
     * 上一次阅读的章节内容表ID
     */
    private Long preContentId;

    /**
     * 创建时间;
     */
    private LocalDateTime createTime;

    /**
     * 更新时间;
     */
    private LocalDateTime updateTime;


}
