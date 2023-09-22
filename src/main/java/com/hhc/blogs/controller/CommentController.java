package com.hhc.blogs.controller;

import com.hhc.blogs.req.CommentSaveReq;
import com.hhc.blogs.resp.CommentListResp;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 按时间获取指定博客的评论
     * */
    @GetMapping("/list/{blogId}")
    public CommonResp<List<CommentListResp>> commentList(@PathVariable Long blogId) {
        CommonResp<List<CommentListResp>> resp = new CommonResp<>();
        List<CommentListResp> commentList = commentService.list(blogId);
        resp.setContent(commentList);
        return resp;
    }

    /**
     * 发布评论
     * */
    @PostMapping("/save")
    public CommonResp<Object> commentSave(@RequestBody CommentSaveReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        commentService.save(req);
        return resp;
    }

    /**
     * 获取博客评论数
     * */
    @GetMapping("/commentNum/{blogId}")
    public CommonResp<Integer> commentNum(@PathVariable Long blogId) {
        CommonResp<Integer> resp = new CommonResp<>();
        Integer commentNum = commentService.commentNum(blogId);
        resp.setContent(commentNum);
        return resp;
    }
}