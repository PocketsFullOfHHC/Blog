package com.hhc.blogs.job;

import com.hhc.blogs.service.BlogSnapshotService;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BlogSnapshotJob {

    private static final Logger LOG = LoggerFactory.getLogger(BlogSnapshotJob.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private BlogSnapshotService blogSnapshotService;

    /**
     * 自定义cron表达式跑批
     * 只有等上一次执行完成，下一次才会在下一个时间点执行，错过就错过
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void doSnapshot() {
        // 增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        LOG.info("生成今日博客快照开始");
        Long start = System.currentTimeMillis();
        blogSnapshotService.genSnapshot();
        LOG.info("生成今日博客快照结束，耗时：{}毫秒", System.currentTimeMillis() - start);
    }
}
