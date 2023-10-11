package com.hhc.blogs.domain;

import java.util.Date;

public class Collect {
    private Long id;
    private Long collectorId;
    private Long blogId;
    private Date collectTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectorId) {
        this.collectorId = collectorId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Collect{");
        sb.append("id=").append(id);
        sb.append(", collectorId=").append(collectorId);
        sb.append(", blogId=").append(blogId);
        sb.append(", collectTime=").append(collectTime);
        sb.append('}');
        return sb.toString();
    }
}
