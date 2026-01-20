package com.rolinsf.novelbackend.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rolinsf.novelbackend.core.constant.CacheConst;
import com.rolinsf.novelbackend.core.constant.DatabaseConst;
import com.rolinsf.novelbackend.dao.entity.BookContent;
import com.rolinsf.novelbackend.dao.mapper.BookContentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author ljq4946
 */
@Component
@RequiredArgsConstructor
public class BookContentCacheManager {

    private final BookContentMapper bookContentMapper;

    /**
     * 查询小说内容，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConst.REDIS_CACHE_MANAGER,
            value = CacheConst.BOOK_CONTENT_CACHE_NAME)
    public String getBookContent(Long chapterId) {
        QueryWrapper<BookContent> contentQueryWrapper = new QueryWrapper<>();
        contentQueryWrapper.eq(DatabaseConst.BookContentTable.COLUMN_CHAPTER_ID, chapterId)
                .last(DatabaseConst.SqlEnum.LIMIT_1.getSql());
        BookContent bookContent = bookContentMapper.selectOne(contentQueryWrapper);
        return bookContent.getContent();
    }

    @CacheEvict(cacheManager = CacheConst.REDIS_CACHE_MANAGER,
            value = CacheConst.BOOK_CONTENT_CACHE_NAME)
    public void evictBookContentCache(Long chapterId) {
        // 调用此方法自动清除小说内容信息的缓存
    }
}
