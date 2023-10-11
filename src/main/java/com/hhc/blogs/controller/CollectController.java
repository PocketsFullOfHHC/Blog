package com.hhc.blogs.controller;

import com.hhc.blogs.req.CollectSaveReq;
import com.hhc.blogs.resp.BlogListResp;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.CollectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    /**
     * 收藏
     * */
    @PostMapping("/save")
    public CommonResp<Object> collectSave(@Valid @RequestBody CollectSaveReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        collectService.save(req);
        return resp;
    }

    /**
     * 取消收藏
     * */
    @GetMapping("/delete/{userId}/{blogId}")
    public CommonResp<Object> deleteCollect(@PathVariable Long userId, @PathVariable Long blogId) {
        CommonResp<Object> resp = new CommonResp<>();
        collectService.deleteByUserIdAndBlogId(userId, blogId);
        return resp;
    }

    /**
     * 获取用户收藏的全部博客
     * */
    @GetMapping("/collectBlogList/{userId}")
    public CommonResp<List<BlogListResp>> collectSave(@PathVariable Long userId) {
        CommonResp<List<BlogListResp>> resp = new CommonResp<>();
        List<BlogListResp> blogListResp = collectService.collectBlogList(userId);
        resp.setContent(blogListResp);
        return resp;
    }
}