package com.hhc.blogs.controller;

import com.hhc.blogs.req.LikesSaveReq;
import com.hhc.blogs.resp.*;
import com.hhc.blogs.service.LikesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikesController {

    @Resource
    private LikesService likesService;

    /**
     * 按时间获取指定用户的点赞点踩信息
     * */
    @GetMapping("/list/{userId}")
    public CommonResp<List<LikesListResp>> likesList(@PathVariable Long userId) {
        CommonResp<List<LikesListResp>> resp = new CommonResp<>();
        List<LikesListResp> likesList = likesService.list(userId);
        resp.setContent(likesList);
        return resp;
    }

    /**
     * 获取关注列表
     * */
    @GetMapping("/blogList/{userId}")
    public CommonResp<List<BlogListResp>> LikesListIncludeInfo(@PathVariable Long userId){
        CommonResp<List<BlogListResp>> resp = new CommonResp<>();
        List<BlogListResp> blogList = likesService.likeList(userId);
        resp.setContent(blogList);
        return resp;
    }

    /**
     * 按时间获取指定博客的点赞点踩信息
     * */
    @GetMapping("/listByBlog/{blogId}")
    public CommonResp<List<LikesListByBlogResp>> likesListByBlog(@PathVariable Long blogId) {
        CommonResp<List<LikesListByBlogResp>> resp = new CommonResp<>();
        List<LikesListByBlogResp> likesList = likesService.listByBlog(blogId);
        resp.setContent(likesList);
        return resp;
    }

    /**
     * 点赞点踩
     * */
    @PostMapping("/save")
    public CommonResp<Object> likesSave(@RequestBody LikesSaveReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        likesService.save(req);
        return resp;
    }

    /**
     * 获取指定用户点赞数
     * */
    @GetMapping("/likesNum/{userId}")
    public CommonResp<Integer> likesNum(@PathVariable Long userId) {
        CommonResp<Integer> resp = new CommonResp<>();
        Integer likesNum = likesService.likesNum(userId);
        resp.setContent(likesNum);
        return resp;
    }
}