package com.hhc.blogs.controller;

import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
