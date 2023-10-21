package com.hhc.blogs.req;

import java.util.Date;

public class CircleBlogSaveReq {
    private Long authorId;
    private Long circleId;
    private Date publishTime;
    private String content;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CircleBlog{");
        sb.append(", authorId=").append(authorId);
        sb.append(", circleId=").append(circleId);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
