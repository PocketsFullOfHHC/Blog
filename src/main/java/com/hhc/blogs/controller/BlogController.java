package com.hhc.blogs.controller;

import com.hhc.blogs.req.BlogPublishReq;
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

    @GetMapping("/list")
    public CommonResp<List<BlogResp>> blogList() {
        CommonResp<List<BlogResp>> resp = new CommonResp<>();
        List<BlogResp> list = blogService.list();
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/publish")
    public CommonResp blogPublish(@RequestBody BlogPublishReq blogPublishReq) {
        CommonResp<Object> commonResp = new CommonResp<>();
        blogService.publish(blogPublishReq);
        return commonResp;
    }
}