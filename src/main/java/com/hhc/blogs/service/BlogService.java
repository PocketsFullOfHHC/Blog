package com.hhc.blogs.service;

import com.hhc.blogs.domain.Blog;
import com.hhc.blogs.mapper.BlogMapper;
import com.hhc.blogs.req.BlogPublishReq;
import com.hhc.blogs.resp.BlogResp;
import com.hhc.blogs.util.CopyUtil;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 测试blog
     * */
    public List<BlogResp> list(){
        List<Blog> blogList = blogMapper.selectByExampleWithBLOBs(null);
        List<BlogResp> blog = CopyUtil.copyList(blogList, BlogResp.class);
        return blog;
    }

    /**
     * 发布blog
     * */
    public void publish(BlogPublishReq blogPublishReq){
        LOG.info("开始注册用户");
        Blog blog = CopyUtil.copy(blogPublishReq, Blog.class);
        blog.setId(snowFlake.nextId());
        blog.setPublishTime(new Date());
        blog.setVoteNum(0);
        blogMapper.insert(blog);
        LOG.info("博客发布成功");
    }

}
