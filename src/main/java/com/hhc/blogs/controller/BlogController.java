package com.hhc.blogs.controller;

import com.hhc.blogs.req.BlogEditReq;
import com.hhc.blogs.req.BlogPublishReq;
import com.hhc.blogs.resp.BlogListResp;
import com.hhc.blogs.resp.BlogResp;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    private BlogService blogService;

    /**
     * 测试功能
     * */
    @GetMapping("/list")
    public CommonResp<List<BlogResp>> blogList() {
        CommonResp<List<BlogResp>> resp = new CommonResp<>();
        List<BlogResp> list = blogService.list();
        resp.setContent(list);
        return resp;
    }

    /**
     * 发布博客
     * */
    @PostMapping("/publish")
    public CommonResp<Object> blogPublish(@RequestBody BlogPublishReq blogPublishReq) {
        CommonResp<Object> commonResp = new CommonResp<>();
        blogService.publish(blogPublishReq);
        return commonResp;
    }

    /**
     * 分页查找我的博客
     * */
    @GetMapping("/myList/{userId}/{pageNum}/{pageSize}")
    public CommonResp<List<BlogListResp>> blogMyList(@PathVariable Long userId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        CommonResp<List<BlogListResp>> resp = new CommonResp<>();
        List<BlogListResp> blogListRespList = blogService.myList(userId, pageNum, pageSize);
        resp.setContent(blogListRespList);
        return resp;
    }

    /**
     * 获取我的分页数据总条数
     * */
    @GetMapping("/myBlogNum/{userId}")
    public CommonResp<Object> blogNum(@PathVariable Long userId){
        CommonResp<Object> resp = new CommonResp<>();
        Integer blogNum = blogService.blogNum(userId);
        resp.setContent(blogNum);
        return resp;
    }

    /**
     * 查找全部博客
     * */
    @GetMapping("/AllList")
    public CommonResp<List<BlogListResp>> blogAllList() {
        CommonResp<List<BlogListResp>> resp = new CommonResp<>();
        List<BlogListResp> blogListRespList = blogService.AllList();
        resp.setContent(blogListRespList);
        return resp;
    }

    /**
     * 分页查询我的朋友博客
     * */
    @GetMapping("/myFollowBlogListByPage/{pageNum}/{pageSize}/{userId}")
    public CommonResp<List<BlogListResp>> myFollowBlogListByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @PathVariable Long userId) {
        CommonResp<List<BlogListResp>> resp = new CommonResp<>();
        List<BlogListResp> blogList = blogService.followBlogListByPage(pageNum, pageSize, userId);
        resp.setContent(blogList);
        return resp;
    }

    /**
     * 获取我的朋友博客总数
     * */
    @GetMapping("/myFollowBlogNum/{userId}")
    public CommonResp<Object> myFollowBlogNum(@PathVariable Long userId) {
        CommonResp<Object> resp = new CommonResp<>();
        Integer blogNum = blogService.myFollowBlogNum(userId);
        resp.setContent(blogNum);
        return resp;
    }

    /**
     * 编辑博客
     * */
    @PostMapping("/edit")
    public CommonResp<Object> blogSave(@Valid @RequestBody BlogEditReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        blogService.edit(req);
        return resp;
    }

    /**
     * 删除博客
     * */
    @GetMapping("/deleteByBlogId/{blogId}")
    public CommonResp<Object> deleteBlogById(@PathVariable Long blogId) {
        CommonResp<Object> resp = new CommonResp<>();
        blogService.deleteBlogById(blogId);
        return resp;
    }

    /**
     * 获取博客总条数
     * */
    @GetMapping("/BlogNum")
    public CommonResp<Object> AllBlogNum(){
        CommonResp<Object> resp = new CommonResp<>();
        Integer blogNum = blogService.blogNumAll();
        resp.setContent(blogNum);
        return resp;
    }
}