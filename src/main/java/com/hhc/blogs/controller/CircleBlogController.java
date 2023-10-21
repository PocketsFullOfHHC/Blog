package com.hhc.blogs.controller;

import com.hhc.blogs.req.CircleBlogSaveReq;
import com.hhc.blogs.resp.BlogListResp;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.CircleBlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/circleBlog")
public class CircleBlogController {

    @Resource
    private CircleBlogService circleBlogService;

    /**
     * 发布部落博客
     * */
    @PostMapping("/saveCircleBlog")
    public CommonResp<Object> blogSave(@Valid @RequestBody CircleBlogSaveReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        circleBlogService.saveCircleBlog(req);
        return resp;
    }

    /**
     * 删除某个部落博客
     * */
    @GetMapping("/deleteCircleBlog/{blogId}")
    public CommonResp<Object> deleteCircleBlog(@PathVariable Long blogId) {
        CommonResp<Object> resp = new CommonResp<>();
        circleBlogService.deleteById(blogId);
        return resp;
    }

    /**
     * 获取某个部落里的博客数量
     * */
    @GetMapping("/circleBlogListNum/{circleId}")
    public CommonResp<Integer> circleBlogListNum(@PathVariable Long circleId) {
        CommonResp<Integer> resp = new CommonResp<>();
        Integer blogListInCircleNum = circleBlogService.circleBlogListNum(circleId);
        resp.setContent(blogListInCircleNum);
        return resp;
    }

    /**
     * 分页获取某部落的全部博客
     * */
    @GetMapping("/circleBlogListByPage/{circleId}/{pageNum}/{pageSize}")
    public CommonResp<List<BlogListResp>> circleBlogListByPage(@PathVariable Long circleId, @PathVariable int pageNum, @PathVariable int pageSize) {
        CommonResp<List<BlogListResp>> resp = new CommonResp<>();
        List<BlogListResp> blogList = circleBlogService.circleBlogListByPage(circleId, pageNum, pageSize);
        resp.setContent(blogList);
        return resp;
    }
}