package com.rolinsf.novelbackend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.rolinsf.novelbackend.core.resp.RestResp;
import com.rolinsf.novelbackend.dto.resp.ImgVerifyCodeRespDto;
import com.rolinsf.novelbackend.manager.VerifyCodeManager;
import com.rolinsf.novelbackend.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author ljq4946
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final VerifyCodeManager verifyCodeManager;

    @Value("${novel.file.upload.path}")
    private String fileUploadPath;

    @Override
    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
        String sessionId = IdWorker.get32UUID();
        return RestResp.ok(ImgVerifyCodeRespDto.builder()
                .sessionId(sessionId)
                .img(verifyCodeManager.genImgVerifyCode(sessionId))
                .build());
    }

}
