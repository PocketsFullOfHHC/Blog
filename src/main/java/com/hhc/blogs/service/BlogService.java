package com.hhc.blogs.service;

import com.hhc.blogs.domain.Blog;
import com.hhc.blogs.mapper.BlogMapper;
import com.hhc.blogs.resp.BlogResp;
import com.hhc.blogs.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogService {
    @Resource
    private BlogMapper blogMapper;

    public List<BlogResp> list(){
        List<Blog> blogList = blogMapper.selectByExampleWithBLOBs(null);
        List<BlogResp> blog = CopyUtil.copyList(blogList, BlogResp.class);
        return blog;
    }
}
