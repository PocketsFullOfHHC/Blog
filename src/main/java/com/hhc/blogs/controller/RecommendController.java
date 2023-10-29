package com.hhc.blogs.controller;

import com.hhc.blogs.domain.Circle;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.resp.UserInfoResp;
import com.hhc.blogs.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendController.class);

    @Resource
    private RecommendService recommendService;

    /**
     * 测试
     * */
    @GetMapping("/test")
    public CommonResp<Object> clusterUser(){
        CommonResp<Object> resp = new CommonResp<>();
        recommendService.test();
        return resp;
    }

    /**
     * 好友推荐
     * */
    @GetMapping("/clusterUser/{name}")
    public CommonResp<List<UserInfoResp>> clusterUser(@PathVariable String name){
        CommonResp<List<UserInfoResp>> resp = new CommonResp<>();
        List<UserInfoResp> userCluster = recommendService.getUserCluster(name);
        resp.setContent(userCluster);
        return resp;
    }

    /**
     * 部落推荐
     * */
    @GetMapping("/clusterCircle")
    public CommonResp<List<Circle>> clusterCircle(){
        CommonResp<List<Circle>> resp = new CommonResp<>();
        List<Circle> CircleCluster = recommendService.getCircleCluster();
        resp.setContent(CircleCluster);
        return resp;
    }
}
