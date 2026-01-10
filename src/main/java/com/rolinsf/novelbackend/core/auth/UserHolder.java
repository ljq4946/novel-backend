package com.rolinsf.novelbackend.core.auth;

import lombok.experimental.UtilityClass;

/**
 * 用户信息 持有类
 *
 * @author ljq4946
 */
@UtilityClass
public class UserHolder {

    /**
     * 当前线程用户ID
     */
    private static final ThreadLocal<Long> USER_IDTL = new ThreadLocal<>();

    /**
     * 当前线程作家ID
     */
    private static final ThreadLocal<Long> AUTHOR_IDTL = new ThreadLocal<>();

    public void setUserId(Long userId) {
        USER_IDTL.set(userId);
    }

    public Long getUserId() {
        return USER_IDTL.get();
    }

    public void setAuthorId(Long authorId) {
        AUTHOR_IDTL.set(authorId);
    }

    public Long getAuthorId() {
        return AUTHOR_IDTL.get();
    }

    public void clear() {
        USER_IDTL.remove();
        AUTHOR_IDTL.remove();
    }

}