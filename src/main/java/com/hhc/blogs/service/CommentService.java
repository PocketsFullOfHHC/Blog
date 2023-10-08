package com.hhc.blogs.service;

import com.hhc.blogs.domain.Comment;
import com.hhc.blogs.mapper.BlogMapperCust;
import com.hhc.blogs.mapper.CommentMapper;
import com.hhc.blogs.mapper.CommentMapperCust;
import com.hhc.blogs.req.CommentSaveReq;
import com.hhc.blogs.resp.CommentListResp;
import com.hhc.blogs.util.CopyUtil;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private CommentMapperCust commentMapperCust;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private BlogMapperCust blogMapperCust;

    /**
     * 按时间获取指定博客的评论
     * */
    public List<CommentListResp> list(Long blogId){
        List<CommentListResp> commentList = commentMapperCust.getCommentList(blogId);
        return commentList;
    }

    /**
     * 发布评论
     * */
    public void save(CommentSaveReq req){
        LOG.info("开始发布评论：{}", req);
        Comment comment = CopyUtil.copy(req, Comment.class);
        comment.setId(snowFlake.nextId());
        comment.setCommentTime(new Date());
        commentMapper.insert(comment);
        blogMapperCust.increaseComment(req.getBlogId());
        LOG.info("评论发布成功");
    }

    /**
     * 获取博客的评论数
     * */
    public Integer commentNum(Long blogId){
        List<CommentListResp> commentList = commentMapperCust.getCommentList(blogId);
        return commentList.size();
    }
}
