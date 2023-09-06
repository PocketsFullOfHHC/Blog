package com.hhc.blogs.service;

import com.hhc.blogs.domain.Blog;
import com.hhc.blogs.mapper.BlogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogService {
    @Resource
    private BlogMapper blogMapper;

    public List<Blog> list(){
        List<Blog> blog = blogMapper.selectByExampleWithBLOBs(null);
        return blog;
    }
}
