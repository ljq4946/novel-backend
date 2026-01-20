package com.rolinsf.novelbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rolinsf.novelbackend.core.annotation.Lock;
import com.rolinsf.novelbackend.core.annotation.Key;
import com.rolinsf.novelbackend.core.auth.UserHolder;
import com.rolinsf.novelbackend.core.constant.DatabaseConst;
import com.rolinsf.novelbackend.core.constant.ErrorCodeEnum;
import com.rolinsf.novelbackend.dao.entity.BookChapter;
import com.rolinsf.novelbackend.dao.entity.BookComment;
import com.rolinsf.novelbackend.dao.entity.BookInfo;
import com.rolinsf.novelbackend.dao.mapper.BookChapterMapper;
import com.rolinsf.novelbackend.dao.mapper.BookCommentMapper;
import com.rolinsf.novelbackend.dao.mapper.BookInfoMapper;
import com.rolinsf.novelbackend.dto.req.PageReqDto;
import com.rolinsf.novelbackend.dto.req.UserCommentReqDto;
import com.rolinsf.novelbackend.dto.resp.*;
import com.rolinsf.novelbackend.manager.*;
import com.rolinsf.novelbackend.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 小说模块 服务实现类
 * @author ljq4946
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookInfoMapper bookInfoMapper;

    private final BookCommentMapper bookCommentMapper;

    private final BookCategoryCacheManager bookCategoryCacheManager;

    private final BookInfoCacheManager bookInfoCacheManager;

    private final BookChapterMapper bookChapterMapper;

    private final BookChapterCacheManager bookChapterCacheManager;

    private final BookContentCacheManager bookContentCacheManager;

    private final BookRankCacheManager bookRankCacheManager;

    private static final Integer REC_BOOK_COUNT = 4;

    @Lock(prefix = "userComment")
    @Override
    public RestResp<Void> saveComment(
            @Key(expr = "#{userId + '::' + bookId}") UserCommentReqDto dto) {
        // 校验书籍是否存在
        BookInfo bookInfo = bookInfoMapper.selectById(dto.getBookId());
        if (bookInfo == null) {
            return RestResp.fail(ErrorCodeEnum.BOOK_NOT_FOUND);
        }
        // 校验用户是否已发表评论
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.BookCommentTable.COLUMN_USER_ID, dto.getUserId())
                .eq(DatabaseConst.BookCommentTable.COLUMN_BOOK_ID, dto.getBookId());
        if (bookCommentMapper.selectCount(queryWrapper) > 0) {
            // 用户已发表评论
            return RestResp.fail(ErrorCodeEnum.USER_COMMENTED);
        }
        BookComment bookComment = new BookComment();
        bookComment.setBookId(dto.getBookId());
        bookComment.setUserId(dto.getUserId());
        bookComment.setCommentContent(dto.getCommentContent());
        bookComment.setCreateTime(LocalDateTime.now());
        bookComment.setUpdateTime(LocalDateTime.now());
        bookCommentMapper.insert(bookComment);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> deleteComment(Long userId, Long commentId) {
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.CommonColumnEnum.ID.getName(), commentId)
                .eq(DatabaseConst.BookCommentTable.COLUMN_USER_ID, userId);
        bookCommentMapper.delete(queryWrapper);
        return RestResp.ok();
    }

    @Override
    public RestResp<PageRespDto<UserCommentRespDto>> listComments(Long userId, PageReqDto pageReqDto) {
        IPage<BookComment> page = new Page<>();
        page.setCurrent(pageReqDto.getPageNum());
        page.setSize(pageReqDto.getPageSize());
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.BookCommentTable.COLUMN_USER_ID, userId)
                .orderByDesc(DatabaseConst.CommonColumnEnum.UPDATE_TIME.getName());
        IPage<BookComment> bookCommentPage = bookCommentMapper.selectPage(page, queryWrapper);
        List<BookComment> comments = bookCommentPage.getRecords();

        List<UserCommentRespDto> commentRespDtoList = Collections.emptyList();
        if (!CollectionUtils.isEmpty(comments)) {
            List<Long> bookIds = comments.stream().map(BookComment::getBookId).toList();
            QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
            bookInfoQueryWrapper.in(DatabaseConst.CommonColumnEnum.ID.getName(), bookIds);
            Map<Long, BookInfo> bookInfoMap = bookInfoMapper.selectList(bookInfoQueryWrapper).stream()
                    .collect(Collectors.toMap(BookInfo::getId, Function.identity()));

            commentRespDtoList = comments.stream().map(v -> {
                BookInfo bookInfo = bookInfoMap.get(v.getBookId());
                return UserCommentRespDto.builder()
                        .commentContent(v.getCommentContent())
                        .commentBook(bookInfo != null ? bookInfo.getBookName() : null)
                        .commentBookPic(bookInfo != null ? bookInfo.getPicUrl() : null)
                        .commentTime(v.getCreateTime())
                        .build();
            }).toList();
        }

        return RestResp.ok(PageRespDto.of(pageReqDto.getPageNum(), pageReqDto.getPageSize(),
                page.getTotal(), commentRespDtoList));
    }

    @Override
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return RestResp.ok(bookCategoryCacheManager.listCategory(workDirection));
    }

    @Override
    public RestResp<BookInfoRespDto> getBookById(Long bookId) {
        return RestResp.ok(bookInfoCacheManager.getBookInfo(bookId));
    }

    @Override
    public RestResp<Void> addVisitCount(Long bookId) {
        bookInfoMapper.addVisitCount(bookId);
        return RestResp.ok();
    }

    @Override
    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId) {
        // 查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookId);

        // 查询最新章节信息
        BookChapterRespDto bookChapter = bookChapterCacheManager.getChapter(
                bookInfo.getLastChapterId());

        // 查询章节内容
        String content = bookContentCacheManager.getBookContent(bookInfo.getLastChapterId());

        // 查询章节总数
        QueryWrapper<BookChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq(DatabaseConst.BookChapterTable.COLUMN_BOOK_ID, bookId);
        Long chapterTotal = bookChapterMapper.selectCount(chapterQueryWrapper);

        // 组装数据并返回
        return RestResp.ok(BookChapterAboutRespDto.builder()
                .chapterInfo(bookChapter)
                .chapterTotal(chapterTotal)
                .contentSummary(content.substring(0, 30))
                .build());
    }

    @Override
    public RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId)
        throws NoSuchAlgorithmException {
            Long categoryId = bookInfoCacheManager.getBookInfo(bookId).getCategoryId();
            List<Long> lastUpdateIdList = bookInfoCacheManager.getLastUpdateIdList(categoryId);

            // 检查列表是否为空或不足
            if (CollectionUtils.isEmpty(lastUpdateIdList)) {
                return RestResp.ok(Collections.emptyList());
            }

            // 排除当前书籍，同时确保有足够的推荐书籍用于展示
            List<Long> candidateIdList = lastUpdateIdList.stream()
                    .filter(id -> !Objects.equals(id, bookId))
                    .toList();

            if (candidateIdList.isEmpty()) {
                return RestResp.ok(Collections.emptyList());
            }

            // 确定实际推荐的书籍数量
            int actualRecCount = Math.min(REC_BOOK_COUNT, candidateIdList.size());

            List<BookInfoRespDto> respDtoList = new ArrayList<>();
            Set<Integer> recIdIndexSet = new HashSet<>();
            Random rand = SecureRandom.getInstanceStrong();

            // 使用 Set 提高查找效率，同时修复bug防止无限循环
            while (respDtoList.size() < actualRecCount && recIdIndexSet.size() < candidateIdList.size()) {
                int recIdIndex = rand.nextInt(candidateIdList.size());
                if (!recIdIndexSet.contains(recIdIndex)) {
                    recIdIndexSet.add(recIdIndex);
                    Long recBookId = candidateIdList.get(recIdIndex);
                    BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(recBookId);
                    respDtoList.add(bookInfo);
                }
            }

            return RestResp.ok(respDtoList);
        }

    @Override
    public RestResp<List<BookChapterRespDto>> listChapters(Long bookId) {
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.BookChapterTable.COLUMN_BOOK_ID, bookId)
                .orderByAsc(DatabaseConst.BookChapterTable.COLUMN_CHAPTER_NUM);
        return RestResp.ok(bookChapterMapper.selectList(queryWrapper).stream()
                .map(v -> BookChapterRespDto.builder()
                        .id(v.getId())
                        .chapterName(v.getChapterName())
                        .build()).toList());
    }

    @Override
    public RestResp<Long> getPreChapterId(Long chapterId) {
        // 查询小说ID 和 章节号
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = chapter.getBookId();
        Integer chapterNum = chapter.getChapterNum();

        // 查询上一章信息并返回章节ID
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.BookChapterTable.COLUMN_BOOK_ID, bookId)
                .lt(DatabaseConst.BookChapterTable.COLUMN_CHAPTER_NUM, chapterNum)
                .orderByDesc(DatabaseConst.BookChapterTable.COLUMN_CHAPTER_NUM)
                .last(DatabaseConst.SqlEnum.LIMIT_1.getSql());
        return RestResp.ok(
                Optional.ofNullable(bookChapterMapper.selectOne(queryWrapper))
                        .map(BookChapter::getId)
                        .orElse(null)
        );
    }

    @Override
    public RestResp<Long> getNextChapterId(Long chapterId) {
        // 查询小说ID 和 章节号
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = chapter.getBookId();
        Integer chapterNum = chapter.getChapterNum();

        // 查询下一章信息并返回章节ID
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.BookChapterTable.COLUMN_BOOK_ID, bookId)
                .gt(DatabaseConst.BookChapterTable.COLUMN_CHAPTER_NUM, chapterNum)
                .orderByAsc(DatabaseConst.BookChapterTable.COLUMN_CHAPTER_NUM)
                .last(DatabaseConst.SqlEnum.LIMIT_1.getSql());
        return RestResp.ok(
                Optional.ofNullable(bookChapterMapper.selectOne(queryWrapper))
                        .map(BookChapter::getId)
                        .orElse(null)
        );
    }

    @Override
    public RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId) {
        log.debug("userId:{}", UserHolder.getUserId());
        // 查询章节信息
        BookChapterRespDto bookChapter = bookChapterCacheManager.getChapter(chapterId);

        // 查询章节内容
        String content = bookContentCacheManager.getBookContent(chapterId);

        // 查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookChapter.getBookId());

        // 组装数据并返回
        return RestResp.ok(BookContentAboutRespDto.builder()
                .bookInfo(bookInfo)
                .chapterInfo(bookChapter)
                .bookContent(content)
                .build());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listVisitRankBooks() {
        return RestResp.ok(bookRankCacheManager.listVisitRankBooks());
    }
}
