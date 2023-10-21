package com.hhc.blogs.controller;

import com.hhc.blogs.domain.Circle;
import com.hhc.blogs.domain.User;
import com.hhc.blogs.req.CircleCreateReq;
import com.hhc.blogs.req.CircleUpdateReq;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.CircleBlogService;
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

    @Resource
    private CircleBlogService circleBlogService;

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
        circleBlogService.deleteByCircleId(circleId);
        circleService.deleteMembersFromCircle(circleId);
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

    /**
     * 加入部落
     * */
    @GetMapping("/joinCircle/{userId}/{circleId}/{managerId}")
    public CommonResp<Object> joinCircle(@PathVariable Long userId, @PathVariable Long circleId, @PathVariable Long managerId){
        CommonResp<Object> resp = new CommonResp<>();
        circleService.joinCircle(userId, circleId, managerId);
        return resp;
    }

    /**
     * 退出部落
     * */
    @GetMapping("/exitCircle/{userId}/{circleId}")
    public CommonResp<Object> exitCircle(@PathVariable Long userId, @PathVariable Long circleId){
        CommonResp<Object> resp = new CommonResp<>();
        circleService.exitCircle(userId, circleId);
        return resp;
    }

    /**
     * 获取指定人加入的部落
     * */
    @GetMapping("/myJoinedCircle/{userId}")
    public CommonResp<List<Circle>> myJoinedCircle(@PathVariable Long userId){
        CommonResp<List<Circle>> resp = new CommonResp<>();
        List<Circle> circles = circleService.myJoinedCircle(userId);
        resp.setContent(circles);
        return resp;
    }

    /**
     * 获取指定部落里的全部成员
     * */
    @GetMapping("/circleMember/{circleId}")
    public CommonResp<List<User>> circleMember(@PathVariable Long circleId){
        CommonResp<List<User>> resp = new CommonResp<>();
        List<User> users = circleService.circleMember(circleId);
        resp.setContent(users);
        return resp;
    }

    /**
     * 删除部落里的指定成员
     * */
    @GetMapping("/deleteMemberFromCircle/{memberId}/{circleId}")
    public CommonResp<Object> circleMember(@PathVariable Long memberId, @PathVariable Long circleId){
        CommonResp<Object> resp = new CommonResp<>();
        circleService.exitCircle(memberId, circleId);
        return resp;
    }

}