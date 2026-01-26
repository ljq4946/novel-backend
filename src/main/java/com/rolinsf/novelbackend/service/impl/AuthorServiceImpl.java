package com.rolinsf.novelbackend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rolinsf.novelbackend.core.constant.CacheConst;
import com.rolinsf.novelbackend.core.constant.ErrorCodeEnum;
import com.rolinsf.novelbackend.core.util.ImgVerifyCodeUtils;
import com.rolinsf.novelbackend.dao.entity.AuthorInfo;
import com.rolinsf.novelbackend.dao.mapper.AuthorInfoMapper;
import com.rolinsf.novelbackend.dto.AuthorInfoDto;
import com.rolinsf.novelbackend.dto.req.AuthorEmaCodeReqDto;
import com.rolinsf.novelbackend.dto.req.AuthorRegisterReqDto;
import com.rolinsf.novelbackend.dto.resp.RestResp;
import com.rolinsf.novelbackend.manager.AuthorInfoCacheManager;
import com.rolinsf.novelbackend.service.AuthorService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author ljq4946
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorInfoCacheManager authorInfoCacheManager;

    private final AuthorInfoMapper authorInfoMapper;

    private final StringRedisTemplate stringRedisTemplate;

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public RestResp<Void> register(AuthorRegisterReqDto dto) {

        // 1. 获取用户输入的验证码和用户ID
        String inputCode = dto.getEmailCode();
        Long userId = dto.getUserId();
        // 校验输入的验证码是否为空
        if (StringUtils.isBlank(inputCode)) {
            return RestResp.fail(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }

        // 2. 从Redis中获取之前存储的邮箱验证码
        String cacheKey = CacheConst.EMAIL_CODE_CACHE_KEY + userId;
        String cacheCode = stringRedisTemplate.opsForValue().get(cacheKey);

        // 3. 验证码验证：先检查是否存在（过期/未发送），再检查是否一致
        if (StringUtils.isBlank(cacheCode)) {
            // 验证码不存在（已过期或未发送）
            return RestResp.fail(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }
        if (!inputCode.equalsIgnoreCase(cacheCode)) {
            // 验证码输入错误
            return RestResp.fail(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }
        // 4. 验证通过：删除Redis中的验证码，避免重复使用
        stringRedisTemplate.delete(cacheKey);

        // 校验该用户是否已注册为作家
        AuthorInfoDto author = authorInfoCacheManager.getAuthor(dto.getUserId());
        if (Objects.nonNull(author)) {
            // 该用户已经是作家，直接返回
            return RestResp.ok();
        }

        // 保存作家注册信息
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.setUserId(dto.getUserId());
        authorInfo.setEmail(dto.getEmail());
        authorInfo.setPenName(dto.getPenName());
        authorInfo.setCreateTime(LocalDateTime.now());
        authorInfo.setUpdateTime(LocalDateTime.now());
        authorInfoMapper.insert(authorInfo);
        // 清除作家缓存
        authorInfoCacheManager.evictAuthorCache();
        return RestResp.ok();
    }

    @Override
    public RestResp<Integer> getStatus(Long userId) {
        AuthorInfoDto author = authorInfoCacheManager.getAuthor(userId);
        return Objects.isNull(author) ? RestResp.ok(null) : RestResp.ok(author.getStatus());
    }

    @Override
    public RestResp<Void> emailCode(AuthorEmaCodeReqDto dto) {
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件发送者
        message.setFrom(from);
        // 设置邮件接收者
        message.setTo(dto.getEmail());
        // 设置邮件的主题
        message.setSubject("登录验证码");
        // 设置邮件的正文
        String emailCode = ImgVerifyCodeUtils.getRandomVerifyCode(4);
        String text = "您的验证码为：" + emailCode + ",请勿泄露给他人。";
        message.setText(text);
        // 发送邮件
        try {
            javaMailSender.send(message);
            stringRedisTemplate.opsForValue().set(CacheConst.EMAIL_CODE_CACHE_KEY + dto.getUserId(),
                    emailCode, Duration.ofMinutes(5));
            return RestResp.ok();
        } catch (MailException ignored) {
            return RestResp.fail(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }

    }
}
