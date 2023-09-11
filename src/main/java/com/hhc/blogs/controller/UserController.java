package com.hhc.blogs.controller;

import com.hhc.blogs.req.UserReq;
import com.hhc.blogs.req.UserSignUpReq;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.resp.UserResp;
import com.hhc.blogs.resp.UserSignUpResp;
import com.hhc.blogs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp<List<UserResp>> list(UserReq req){
        CommonResp<List<UserResp>> resp = new CommonResp<>();
        List<UserResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    /**
     * 注册
     * */
    @PostMapping("/signup")
    public CommonResp<UserSignUpResp> SignUp(@Valid @RequestBody UserSignUpReq userSignUpReq){
        // 后端第二次密码加密
        userSignUpReq.setPassword(DigestUtils.md5DigestAsHex(userSignUpReq.getPassword().getBytes()));
        LOG.info("加密后的密码：{}",userSignUpReq.getPassword());
        UserSignUpResp userSignUpResp = userService.SignUp(userSignUpReq);
        CommonResp<UserSignUpResp> userSignUpRespCommonResp = new CommonResp<>();
        userSignUpRespCommonResp.setContent(userSignUpResp);
        return userSignUpRespCommonResp;
    }
}
