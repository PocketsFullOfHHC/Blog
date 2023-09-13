package com.hhc.blogs.mapper;

import com.hhc.blogs.resp.BlogListResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface BlogMapperCust {
    
    public List<BlogListResp> getBlogList();

    public List<BlogListResp> getMyBlogList(@Param("id") Long id);

}
