package com.hhc.blogs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhc.blogs.domain.Blog;
import com.hhc.blogs.domain.BlogExample;
import com.hhc.blogs.domain.CommentExample;
import com.hhc.blogs.domain.LikesExample;
import com.hhc.blogs.mapper.BlogMapper;
import com.hhc.blogs.mapper.BlogMapperCust;
import com.hhc.blogs.mapper.CommentMapper;
import com.hhc.blogs.mapper.LikesMapper;
import com.hhc.blogs.req.BlogEditReq;
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

    private static final Logger LOG = LoggerFactory.getLogger(BlogService.class);

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private LikesMapper likesMapper;

    @Resource
    private BlogMapperCust blogMapperCust;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private UserService userService;

    @Resource
    private FollowService followService;

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
        blogList.setAvatar(userInfo.getAvatar());
        LOG.info("根据博客ID查找的博客信息为：{}", blogList);
        return blogList;
    }

    /**
     * 获取用户好友的博客
     * */
    public List<BlogListResp> followBlogListByPage(int pageNum, int pageSize, Long userId){
        List<UserInfoResp> followUserList = followService.followList(userId.toString());
        PageHelper.startPage(pageNum, pageSize);
        List<BlogListResp> blogListRespList = blogMapperCust.followBlogList(followUserList);
        PageInfo<BlogListResp> pageInfo = new PageInfo<>(blogListRespList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        return blogListRespList;
    }

    /**
     * 获取我关注的人博客数量
     * */
    public Integer myFollowBlogNum(Long userId){
        List<UserInfoResp> userInfoRespList = followService.followList(userId.toString());
        List<BlogListResp> blogListResp = blogMapperCust.followBlogList(userInfoRespList);
        return blogListResp.size();
    }

    /**
     * 编辑博客
     * */
    public void edit(BlogEditReq req){
        blogMapperCust.editBlogById(req);
    }

    /**
     * 删除博客
     * */
    public void deleteBlogById(Long blogId){
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria commentCriteria = commentExample.createCriteria();
        commentCriteria.andBlogIdEqualTo(blogId);
        LOG.info("删除帖子{}评论",blogId);
        commentMapper.deleteByExample(commentExample);
        LikesExample likesExample = new LikesExample();
        LikesExample.Criteria likesCriteria = likesExample.createCriteria();
        likesCriteria.andBlogIdEqualTo(blogId);
        LOG.info("删除帖子{}点赞点踩",blogId);
        likesMapper.deleteByExample(likesExample);
        BlogExample blogExample = new BlogExample();
        BlogExample.Criteria blogCriteria = blogExample.createCriteria();
        blogCriteria.andIdEqualTo(blogId);
        LOG.info("删除帖子id：{}",blogId);
        blogMapper.deleteByExample(blogExample);
    }
}
