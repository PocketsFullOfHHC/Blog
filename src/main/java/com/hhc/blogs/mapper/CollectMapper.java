package com.hhc.blogs.mapper;

import com.hhc.blogs.domain.Collect;

import java.util.List;

public interface CollectMapper {

    public List<Collect> getCollectListByUser(Long userId);

    public void save(Collect collect);

    public void deleteByUserIdAndBlogId(Long userId, Long blogId);

    public Collect getCollectByUserIdAndBlogId(Long userId, Long blogId);

    public void deleteByBlogId(Long blogId);
}
