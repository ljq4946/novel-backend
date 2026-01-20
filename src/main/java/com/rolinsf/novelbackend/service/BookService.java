package com.rolinsf.novelbackend.service;

import com.rolinsf.novelbackend.dto.req.UserCommentReqDto;
import com.rolinsf.novelbackend.dto.resp.*;
import com.rolinsf.novelbackend.dto.req.PageReqDto;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    /**
     * 小说分类列表查询
     * @param workDirection 作品类别
     * @return 分类列表
     */
    RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection);

    /**
     * 小说信息查询
     * @param bookId 小说ID
     * @return 小说信息
     */
    RestResp<BookInfoRespDto> getBookById(Long bookId);

    /**
     * 增加小说点击量
     *
     * @param bookId 小说ID
     * @return 成功状态
     */
    RestResp<Void> addVisitCount(Long bookId);

    /**
     * 小说最新章节相关信息查询
     * @param bookId 小说ID
     * @return 章节相关联的信息
     */
    RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId);

    /**
     * 小说推荐列表查询
     *
     * @param bookId 小说ID
     * @return 小说信息列表
     */
    RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException;

    /**
     * 小说章节列表查询
     *
     * @param bookId 小说ID
     * @return 小说章节列表
     */
    RestResp<List<BookChapterRespDto>> listChapters(Long bookId);

    /**
     * 获取上一章节ID
     *
     * @param chapterId 章节ID
     * @return 上一章节ID
     */
    RestResp<Long> getPreChapterId(Long chapterId);

    /**
     * 获取下一章节ID
     *
     * @param chapterId 章节ID
     * @return 下一章节ID
     */
    RestResp<Long> getNextChapterId(Long chapterId);

    /**
     * 小说内容相关信息查询
     *
     * @param chapterId 章节ID
     * @return 内容相关联的信息
     */
    RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId);

    /**
     * 小说点击榜查询
     *
     * @return 小说点击排行列表
     */
    RestResp<List<BookRankRespDto>> listVisitRankBooks();

}
