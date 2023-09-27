package com.hhc.blogs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhc.blogs.domain.Blog;
import com.hhc.blogs.domain.BlogExample;
import com.hhc.blogs.mapper.BlogMapper;
import com.hhc.blogs.mapper.BlogMapperCust;
import com.hhc.blogs.req.BlogPublishReq;
import com.hhc.blogs.resp.BlogListResp;
import com.hhc.blogs.resp.BlogResp;
import com.hhc.blogs.resp.UserInfoResp;
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
    private BlogMapperCust blogMapperCust;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private UserService userService;

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
        blog.setOpposeNum(0);
        blog.setCommentNum(0);
        blogMapper.insert(blog);
        LOG.info("博客发布成功");
    }

    /**
     * 按发布时间分页查找我的博客
     * */
    public List<BlogListResp> myList(Long userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<BlogListResp> blogList = blogMapperCust.getMyBlogList(userId);
        PageInfo<BlogListResp> pageInfo = new PageInfo<>(blogList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        return blogList;
    }

    /**
     * 获取我的博客分页数据总条数
     * */
    public Integer blogNum(Long userId){
        List<BlogListResp> blogList = blogMapperCust.getMyBlogList(userId);
        return blogList.size();
    }

    /**
     * 按发布时间查找全部博客
     * */
    public List<BlogListResp> AllList(){
        List<BlogListResp> blogList = blogMapperCust.getBlogList();
        return blogList;
    }

    /**
     * 更新评论数
     * */
    public void increaseComment(Long blogId){
        blogMapperCust.increaseComment(blogId);
    }

    /**
     * 按博客ID查找博客
     * */
    public BlogListResp getBlogById(Long blogId){
        BlogExample blogExample = new BlogExample();
        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andIdEqualTo(blogId);
        List<Blog> blogs = blogMapper.selectByExampleWithBLOBs(blogExample);
        Blog blog = blogs.get(0);
        UserInfoResp userInfo = userService.getUserInfo(blog.getAuthorId());
        BlogListResp blogList = CopyUtil.copy(blog, BlogListResp.class);
        blogList.setAuthorName(userInfo.getName());
        LOG.info("根据博客ID查找的博客信息为：{}", blogList);
        return blogList;
    }
}
