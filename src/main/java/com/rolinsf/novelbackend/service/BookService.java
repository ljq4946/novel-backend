package com.rolinsf.novelbackend.service;

import com.rolinsf.novelbackend.dto.req.UserCommentReqDto;
import com.rolinsf.novelbackend.dto.resp.PageRespDto;
import com.rolinsf.novelbackend.dto.resp.RestResp;
import com.rolinsf.novelbackend.dto.req.PageReqDto;
import com.rolinsf.novelbackend.dto.resp.UserCommentRespDto;

/**
 * 小说模块 服务类
 * @author ljq4946
 */
public interface BookService {

    /**
     * 发表评论
     * @param dto 评论相关 DTO
     * @return void
     */
    RestResp<Void> saveComment(UserCommentReqDto dto);

    /**
     * 删除评论
     * @param userId    评论用户ID
     * @param commentId 评论ID
     * @return void
     */
    RestResp<Void> deleteComment(Long userId, Long commentId);
    /**
     * 分页查询评论
     * @param userId     会员ID
     * @param pageReqDto 分页参数
     * @return 评论分页列表数据
     */
    RestResp<PageRespDto<UserCommentRespDto>> listComments(Long userId, PageReqDto pageReqDto);
}
