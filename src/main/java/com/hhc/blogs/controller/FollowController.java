package com.hhc.blogs.controller;

import com.hhc.blogs.domain.Follow;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.resp.UserInfoResp;
import com.hhc.blogs.service.FollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private static final Logger LOG = LoggerFactory.getLogger(FollowController.class);

    @Resource
    private FollowService followService;

    /**
     * 关注
     * */
    @GetMapping("/follow/{likeId}/{beLikedId}")
    public CommonResp<Follow> follow(@PathVariable String likeId, @PathVariable String beLikedId){
        CommonResp<Follow> resp = new CommonResp<>();
        Follow follow = followService.follow(likeId, beLikedId);
        resp.setContent(follow);
        return resp;
    }

    /**
     * 取关
     * */
    @GetMapping("/unFollow/{likeId}/{beLikedId}")
    public CommonResp<Object> unFollow(@PathVariable String likeId, @PathVariable String beLikedId){
        CommonResp resp = new CommonResp<>();
        followService.unFollow(likeId, beLikedId);
        return resp;
    }

    /**
     * 是否关注
     * */
    @GetMapping("/isFollowed/{likeId}/{beLikedId}")
    public CommonResp<Boolean> ifFollowed(@PathVariable String likeId, @PathVariable String beLikedId){
        CommonResp resp = new CommonResp<>();
        Follow follow = followService.ifFollowed(likeId, beLikedId);
        if (ObjectUtils.isEmpty(follow)){
            resp.setContent(false);
        }else {
            resp.setContent(true);
        }
        return resp;
    }

    /**
     * 获取关注列表
     * */
    @GetMapping("/followList/{likeId}")
    public CommonResp<List<UserInfoResp>> followList(@PathVariable String likeId){
        CommonResp<List<UserInfoResp>> resp = new CommonResp<>();
        List<UserInfoResp> userFollowRespList = followService.followList(likeId);
        resp.setContent(userFollowRespList);
        return resp;
    }
}
