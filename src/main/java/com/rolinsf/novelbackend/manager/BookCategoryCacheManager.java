package com.rolinsf.novelbackend.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rolinsf.novelbackend.core.constant.CacheConst;
import com.rolinsf.novelbackend.core.constant.DatabaseConst;
import com.rolinsf.novelbackend.dao.entity.BookCategory;
import com.rolinsf.novelbackend.dao.mapper.BookCategoryMapper;
import com.rolinsf.novelbackend.dto.resp.BookCategoryRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ljq4946
 */
@Component
@RequiredArgsConstructor
public class BookCategoryCacheManager {

    private final BookCategoryMapper bookCategoryMapper;

    /**
     * 根据作品方向查询小说分类列表，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConst.CAFFEINE_CACHE_MANAGER,
            value = CacheConst.BOOK_CATEGORY_LIST_CACHE_NAME)
    public List<BookCategoryRespDto> listCategory(Integer workDirection) {
        QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConst.BookCategoryTable.COLUMN_WORK_DIRECTION, workDirection);
        return bookCategoryMapper.selectList(queryWrapper).stream().map(v ->
                BookCategoryRespDto.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .build()).toList();
    }

}
