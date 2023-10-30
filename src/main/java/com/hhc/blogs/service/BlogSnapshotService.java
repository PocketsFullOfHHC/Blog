package com.hhc.blogs.service;

import com.hhc.blogs.mapper.BlogSnapshotMapperCust;
import com.hhc.blogs.resp.StatisticResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogSnapshotService {

    private static final Logger LOG = LoggerFactory.getLogger(BlogSnapshotService.class);

    @Resource
    private BlogSnapshotMapperCust blogSnapshotMapperCust;

    /**
     * 定时刷新快照表
     * */
    public void genSnapshot() {
        blogSnapshotMapperCust.genSnapshot();
    }

    /**
     * 获取首页数值数据
     */
    public List<StatisticResp> getStatistic() {
        return blogSnapshotMapperCust.getStatistic();
    }
}
