package com.rolinsf.novelbackend.manager;

import com.rolinsf.novelbackend.core.constant.CacheConst;
import com.rolinsf.novelbackend.core.constant.ErrorCodeEnum;
import com.rolinsf.novelbackend.core.exception.BusinessException;
import com.rolinsf.novelbackend.dto.resp.UserSignRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljq4946
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserSignManager {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 更新连续签到天数
     */
    public void updateContinuousSignDays(Long userId) {
        LocalDate today = LocalDate.now();
        String continuousKey =  CacheConst.USER_SIGN_CONTINUOUS_KEY_PREFIX + userId;

        // 判断昨天是否签到
        boolean yesterdayChecked = isSignedIn(userId, today.minusDays(1));

        if (yesterdayChecked) {
            // 昨天签到了，连续签到天数+1
            stringRedisTemplate.opsForValue().increment(continuousKey);
        } else {
            // 昨天没签到，重置连续签到天数为1
            stringRedisTemplate.opsForValue().set(continuousKey, "1");
        }
    }

    /**
     * 判断用户指定日期是否签到
     */
    public boolean isSignedIn(Long userId, LocalDate date) {
        int day = date.getDayOfMonth();
        String key = buildSignKey(userId);

        Boolean isSigned = stringRedisTemplate.opsForValue().getBit(key, (long)day - 1);
        return isSigned != null && isSigned;
    }


    /**
     * 构建签到Key
     */
    public String buildSignKey(Long userId) {
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return CacheConst.USER_SIGN_CACHE_KEY_PREFIX + userId + "::" + yearMonth;
    }

    /**
     * 执行用户签到
     */
    public UserSignRespDto sign(Long userId) {
        // 获取今日日期，校验是否已签到
        LocalDate today = LocalDate.now();
        int dayOfMonth = today.getDayOfMonth();
        String signKey = buildSignKey(userId);

        Boolean hasSignedToday = stringRedisTemplate.opsForValue().getBit(signKey, (long) dayOfMonth - 1);
        if (hasSignedToday != null && hasSignedToday) {
            throw new BusinessException(ErrorCodeEnum.USER_SIGNED_TODAY);
        }

        // 执行签到
        stringRedisTemplate.opsForValue().setBit(signKey, (long) dayOfMonth - 1, true);

        // 更新连续签到天数
        updateContinuousSignDays(userId);

        // 获取最新连续签到天数
        String continuousKey = CacheConst.USER_SIGN_CONTINUOUS_KEY_PREFIX + userId;
        String daysStr = stringRedisTemplate.opsForValue().get(continuousKey);
        int continuousDays = daysStr != null ? Integer.parseInt(daysStr) : 1;

        // 构建并返回签到响应
        return UserSignRespDto.builder()
                .signTime(LocalDateTime.now())
                .continuousSignDays(continuousDays)
                .build();
    }

    /**
     * 获取用户连续签到天数
     */
    public int getContinuousSignDays(Long userId) {
        String continuousKey = CacheConst.USER_SIGN_CONTINUOUS_KEY_PREFIX + userId;
        String value = stringRedisTemplate.opsForValue().get(continuousKey);
        return value != null ? Integer.parseInt(value) : 0;
    }

    /**
     * 获取用户当月签到情况
     */
    public List<Boolean> getMonthSignData(Long userId) {
        LocalDate today = LocalDate.now();
        List<Boolean> result = new ArrayList<>();
        String key = buildSignKey(userId);
        int dayOfMonth = today.lengthOfMonth();

        for (int i = 0; i < dayOfMonth; i++) {
            Boolean isSigned = stringRedisTemplate.opsForValue().getBit(key, i);
            result.add(isSigned != null && isSigned);
        }
        return result;
    }
}
