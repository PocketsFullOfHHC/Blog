package com.hhc.blogs.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhc.blogs.req.UserLoginReq;
import com.hhc.blogs.req.UserReq;
import com.hhc.blogs.req.UserSignUpReq;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.resp.UserLoginResp;
import com.hhc.blogs.resp.UserResp;
import com.hhc.blogs.resp.UserSignUpResp;
import com.hhc.blogs.service.UserService;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private SnowFlake snowFlake;

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

    /**
     * 登录
     * */
    @PostMapping("/login")
    public CommonResp<UserLoginResp> Login(@Valid @RequestBody UserLoginReq userLoginReq){
        // 后端第二次密码加密
        userLoginReq.setPassword(DigestUtils.md5DigestAsHex(userLoginReq.getPassword().getBytes()));
        LOG.info("加密后的密码：{}",userLoginReq.getPassword());
        UserLoginResp userLoginResp = userService.Login(userLoginReq);
        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token: {}，并放入redis中", token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp), 3600 * 24, TimeUnit.SECONDS);
        CommonResp<UserLoginResp> userLoginRespCommonResp = new CommonResp<>();
        userLoginRespCommonResp.setContent(userLoginResp);
        return userLoginRespCommonResp;
    }

    /**
     * 退出登录
     * */
    @GetMapping("/logout/{token}")
    public CommonResp<UserLoginResp> logout(@PathVariable String token){
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token: {}", token);
        return resp;
    }
}
