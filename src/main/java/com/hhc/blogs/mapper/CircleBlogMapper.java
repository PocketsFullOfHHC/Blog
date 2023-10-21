package com.hhc.blogs.mapper;

import com.hhc.blogs.domain.CircleBlog;
import com.hhc.blogs.resp.BlogListResp;

import java.util.List;

public interface CircleBlogMapper {
    public void saveCircleBlog(CircleBlog circleBlog);

    public void deleteById(Long circleCircleBlogId);

    public void deleteByCircleId(Long circleId);

    public List<BlogListResp> getBlogByCircleId(Long circleId);
}
