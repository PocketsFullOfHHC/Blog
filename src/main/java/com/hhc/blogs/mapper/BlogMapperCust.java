package com.hhc.blogs.mapper;

import com.hhc.blogs.req.BlogEditReq;
import com.hhc.blogs.resp.BlogListResp;
import com.hhc.blogs.resp.UserInfoResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface BlogMapperCust {
    
    public List<BlogListResp> getBlogList();

    public List<BlogListResp> getMyBlogList(@Param("id") Long id);

    public void increaseComment(Long id);

    public List<BlogListResp> followBlogList(@Param("listParam") List<UserInfoResp> followUserList);

    public void editBlogById(BlogEditReq blogEditReq);
}
