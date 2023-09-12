package com.hhc.blogs.req;

import java.util.Date;

public class BlogPublishReq {
    private Long authorId;
    private String content;
    private Date publishTime;
    private String photo;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", authorId=").append(authorId);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", photo=").append(photo);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}