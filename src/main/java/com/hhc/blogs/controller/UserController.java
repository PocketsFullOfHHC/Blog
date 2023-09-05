package com.hhc.blogs.controller;

import com.hhc.blogs.req.UserReq;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.resp.UserResp;
import com.hhc.blogs.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp<List<UserResp>> list(UserReq req){
        CommonResp<List<UserResp>> resp = new CommonResp<>();
        List<UserResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }
}
