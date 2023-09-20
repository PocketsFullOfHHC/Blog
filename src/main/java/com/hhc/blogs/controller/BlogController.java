package com.hhc.blogs.controller;

import com.hhc.blogs.req.BlogPublishReq;
import com.hhc.blogs.resp.BlogListResp;
import com.hhc.blogs.resp.BlogResp;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public CommonResp blogNum(@PathVariable Long userId){
        CommonResp resp = new CommonResp<>();
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
}