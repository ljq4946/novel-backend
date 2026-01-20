package com.rolinsf.novelbackend.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rolinsf.novelbackend.core.constant.CacheConst;
import com.rolinsf.novelbackend.core.constant.DatabaseConst;
import com.rolinsf.novelbackend.dao.entity.BookChapter;
import com.rolinsf.novelbackend.dao.entity.BookInfo;
import com.rolinsf.novelbackend.dao.mapper.BookChapterMapper;
import com.rolinsf.novelbackend.dao.mapper.BookInfoMapper;
import com.rolinsf.novelbackend.dto.resp.BookInfoRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ljq4946
 */
@Component
@RequiredArgsConstructor
public class BookInfoCacheManager {

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterMapper bookChapterMapper;

    /**
     * 从缓存中查询小说信息（先判断缓存中是否已存在，存在则直接从缓存中取，否则执行方法体中的逻辑后缓存结果）
     */
    @Cacheable(cacheManager = CacheConst.CAFFEINE_CACHE_MANAGER,
            value = CacheConst.BOOK_INFO_CACHE_NAME)
    public BookInfoRespDto getBookInfo(Long id) {
        return cachePutBookInfo(id);
    }

    /**
     * 缓存小说信息（不管缓存中是否存在都执行方法体中的逻辑，然后缓存起来）
     */
    @CachePut(cacheManager = CacheConst.CAFFEINE_CACHE_MANAGER,
            value = CacheConst.BOOK_INFO_CACHE_NAME)
    public BookInfoRespDto cachePutBookInfo(Long id) {
        // 查询基础信息
        BookInfo bookInfo = bookInfoMapper.selectById(id);
        // 查询首章ID
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq(DatabaseConst.BookChapterTable.COLUMN_BOOK_ID, id)
                .orderByAsc(DatabaseConst.BookChapterTable.COLUMN_CHAPTER_NUM)
                .last(DatabaseConst.SqlEnum.LIMIT_1.getSql());
        BookChapter firstBookChapter = bookChapterMapper.selectOne(queryWrapper);
        // 组装响应对象
        return BookInfoRespDto.builder()
                .id(bookInfo.getId())
                .bookName(bookInfo.getBookName())
                .bookDesc(bookInfo.getBookDesc())
                .bookStatus(bookInfo.getBookStatus())
                .authorId(bookInfo.getAuthorId())
                .authorName(bookInfo.getAuthorName())
                .categoryId(bookInfo.getCategoryId())
                .categoryName(bookInfo.getCategoryName())
                .commentCount(bookInfo.getCommentCount())
                .firstChapterId(firstBookChapter.getId())
                .lastChapterId(bookInfo.getLastChapterId())
                .picUrl(bookInfo.getPicUrl())
                .visitCount(bookInfo.getVisitCount())
                .wordCount(bookInfo.getWordCount())
                .build();
    }

    @CacheEvict(cacheManager = CacheConst.CAFFEINE_CACHE_MANAGER,
            value = CacheConst.BOOK_INFO_CACHE_NAME)
    public void evictBookInfoCache(Long bookId) {
        // 调用此方法自动清除小说信息的缓存
    }

    /**
     * 查询每个类别下最新更新的 500 个小说ID列表，并放入缓存中 1 个小时
     */
    @Cacheable(cacheManager = CacheConst.CAFFEINE_CACHE_MANAGER,
            value = CacheConst.LAST_UPDATE_BOOK_ID_LIST_CACHE_NAME)
    public List<Long> getLastUpdateIdList(Long categoryId) {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.BookTable.COLUMN_CATEGORY_ID, categoryId)
                .gt(DatabaseConst.BookTable.COLUMN_WORD_COUNT, 0)
                .orderByDesc(DatabaseConst.BookTable.COLUMN_LAST_CHAPTER_UPDATE_TIME)
                .last(DatabaseConst.SqlEnum.LIMIT_500.getSql());
        return bookInfoMapper.selectList(queryWrapper).stream().map(BookInfo::getId).toList();
    }

}
