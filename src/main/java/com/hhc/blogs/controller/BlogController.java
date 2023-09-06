package com.hhc.blogs.controller;

import com.hhc.blogs.domain.Blog;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    private BlogService blogService;

    @GetMapping("/list")
    public CommonResp<List<Blog>> blogList() {
        CommonResp<List<Blog>> resp = new CommonResp<>();
        List<Blog> list = blogService.list();
        resp.setContent(list);
        return resp;
    }
}