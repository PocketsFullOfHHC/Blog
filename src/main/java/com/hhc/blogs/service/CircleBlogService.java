package com.hhc.blogs.service;

import com.github.pagehelper.PageHelper;
import com.hhc.blogs.domain.CircleBlog;
import com.hhc.blogs.mapper.CircleBlogMapper;
import com.hhc.blogs.req.CircleBlogSaveReq;
import com.hhc.blogs.resp.BlogListResp;
import com.hhc.blogs.util.CopyUtil;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CircleBlogService {

    private static final Logger LOG = LoggerFactory.getLogger(CircleBlogService.class);

    @Resource
    private CircleBlogMapper circleBlogMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 发布部落博客
     * */
    public void saveCircleBlog(CircleBlogSaveReq req){
        CircleBlog circleBlog = CopyUtil.copy(req, CircleBlog.class);
        long blogId = snowFlake.nextId();
        circleBlog.setId(blogId);
        circleBlog.setPublishTime(new Date());
        circleBlog.setVoteNum(0);
        circleBlog.setOpposeNum(0);
        circleBlog.setCommentNum(0);
        circleBlogMapper.saveCircleBlog(circleBlog);
    }

    /**
     * 删除部落博客
     * */
    public void deleteById(Long blogId){
        LOG.info("删除帖子id：{}",blogId);
        circleBlogMapper.deleteById(blogId);
    }

    /**
     * 删除某个部落里的全部博客
     * */
    public void deleteByCircleId(Long circleId){
        circleBlogMapper.deleteByCircleId(circleId);
    }

    /**
     * 获取某个部落里的博客数量
     * */
    public Integer circleBlogListNum(Long circleId){
        List<BlogListResp> blogListByCircleId = circleBlogMapper.getBlogByCircleId(circleId);
        return blogListByCircleId.size();
    }

    /**
     * 分页获取某个部落里的全部博客
     * */
    public List<BlogListResp> circleBlogListByPage(Long circleId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<BlogListResp> blogListByCircleId = circleBlogMapper.getBlogByCircleId(circleId);
        return blogListByCircleId;
    }
}
