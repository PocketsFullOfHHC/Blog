package com.hhc.blogs.mapper;

import com.hhc.blogs.domain.Likes;
import com.hhc.blogs.resp.LikesListByBlogResp;
import com.hhc.blogs.resp.LikesListResp;

import java.util.List;

public interface LikesMapperCust {

    public List<LikesListResp> getLikesList(Long userId);

    public List<LikesListByBlogResp> getLikesListByBlog(Long blogId);

    public void save(Likes likes);

    public List<Likes> getLikesRepeat(Likes likes);

}
