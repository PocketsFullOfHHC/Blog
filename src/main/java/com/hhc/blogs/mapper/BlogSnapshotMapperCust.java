package com.hhc.blogs.mapper;

import com.hhc.blogs.resp.StatisticResp;

import java.util.List;

public interface BlogSnapshotMapperCust {
    public void genSnapshot();
    List<StatisticResp> getStatistic();
}
