package com.hhc.blogs.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhc.blogs.req.UserInfoReq;
import com.hhc.blogs.req.UserLoginReq;
import com.hhc.blogs.req.UserReq;
import com.hhc.blogs.req.UserSignUpReq;
import com.hhc.blogs.resp.*;
import com.hhc.blogs.service.UserService;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token: {}", token);
        return resp;
    }

    /**
     * 头像上传
     * */
    @RequestMapping("/upload/avatar")
    public CommonResp uploadAvatar(@RequestHeader(value = "userId",required = false) String userId, @RequestParam MultipartFile avatar) throws IOException {
        LOG.info("上传文件开始：{}", avatar);
        LOG.info("文件名：{}", avatar.getOriginalFilename());
        LOG.info("文件大小：{}", avatar.getSize());
        CommonResp resp = new CommonResp();
        // 图片重命名(带上用户id)
        String originalFilename = avatar.getOriginalFilename();
        String fileName = userId + "." +originalFilename.split("\\.")[1];
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String path = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\statics\\avatars\\" + fileName;
        LOG.info("文件存储地址：{}", path);
        avatar.transferTo(new File(path));
        //修改用户头像
        userService.alterAvatarName(fileName, userId);
        LOG.info("文件上传成功");
        return resp;
    }

    /**
     * 获取用户信息
     * */
    @GetMapping("/info/{userId}")
    public CommonResp<UserInfoResp> getInfo(@PathVariable Long userId){
        CommonResp<UserInfoResp> resp = new CommonResp<UserInfoResp>();
        UserInfoResp userInfoResp = userService.getUserInfo(userId);
        resp.setContent(userInfoResp);
        return resp;
    }

    /**
     * 更改用户信息
     * */
    @PostMapping("/updateInfo")
    public CommonResp<Object> updateInfo(@RequestBody UserInfoReq userInfoReq){
        CommonResp<Object> resp = new CommonResp<>();
        userService.updateUserInfo(userInfoReq);
        return resp;
    }

    /**
     * 根据用户昵称搜索
     * */
    @GetMapping("/getUserInfoByName/{name}")
    public CommonResp<List<UserInfoResp>> getUserInfoByName(@PathVariable String name){
        CommonResp<List<UserInfoResp>> resp = new CommonResp<>();
        List<UserInfoResp> userInfo = userService.getUserInfoByName(name);
        resp.setContent(userInfo);
        return resp;
    }

    /**
     * 根据标签搜索好友
     * */
    @GetMapping("/getUserListByTags/{tag}")
    public CommonResp<List<UserInfoResp>> getUserListByTags(@PathVariable String tag){
        CommonResp<List<UserInfoResp>> resp = new CommonResp<>();
        List<UserInfoResp> userListByTags = userService.getUserListByTags(tag);
        resp.setContent(userListByTags);
        return resp;
    }
}
