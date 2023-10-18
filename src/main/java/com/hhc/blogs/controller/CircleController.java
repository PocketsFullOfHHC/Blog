package com.hhc.blogs.controller;

import com.hhc.blogs.domain.Circle;
import com.hhc.blogs.req.CircleCreateReq;
import com.hhc.blogs.req.CircleUpdateReq;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.CircleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/circle")
public class CircleController {

    @Resource
    private CircleService circleService;

    /**
     * 查询全部的部落信息
     * */
    @GetMapping("/list")
    public CommonResp<List<Circle>> circleList() {
        CommonResp<List<Circle>> resp = new CommonResp<>();
        resp.setContent(circleService.list());
        return resp;
    }

    /**
     * 创建新部落
     * */
    @PostMapping("/create")
    public CommonResp<Object> create(@Valid @RequestBody CircleCreateReq req){
        CommonResp<Object> resp = new CommonResp<>();
        circleService.createCircle(req);
        return resp;
    }

    /**
     * 修改部落信息
     * */
    @PostMapping("/update")
    public CommonResp<Object> update(@Valid @RequestBody CircleUpdateReq req){
        CommonResp<Object> resp = new CommonResp<>();
        circleService.updateCircle(req);
        return resp;
    }

    /**
     * 删除部落
     * */
    @GetMapping("/delete/{circleId}")
    public CommonResp<Object> delete(@PathVariable Long circleId){
        CommonResp<Object> resp = new CommonResp<>();
        circleService.deleteCircle(circleId);
        return resp;
    }

    /**
     * 获取我创建的全部部落
     * */
    @GetMapping("/myCreatedCircle/{userId}")
    public CommonResp<List<Circle>> myCreatedCircle(@PathVariable Long userId){
        CommonResp<List<Circle>> resp = new CommonResp<>();
        List<Circle> circles = circleService.myCreatedCircle(userId);
        resp.setContent(circles);
        return resp;
    }
}