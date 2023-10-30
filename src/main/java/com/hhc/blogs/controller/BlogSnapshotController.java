package com.hhc.blogs.controller;

import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.resp.StatisticResp;
import com.hhc.blogs.service.BlogSnapshotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog-snapshot")
public class BlogSnapshotController {

    @Resource
    private BlogSnapshotService blogSnapshotService;

    /**
     * 获取首页数值数据
     * */
    @GetMapping("/get-statistic")
    public CommonResp<List<StatisticResp>> getStatistic() {
        List<StatisticResp> statisticResp = blogSnapshotService.getStatistic();
        CommonResp<List<StatisticResp>> commonResp = new CommonResp<>();
        commonResp.setContent(statisticResp);
        return commonResp;
    }
}
