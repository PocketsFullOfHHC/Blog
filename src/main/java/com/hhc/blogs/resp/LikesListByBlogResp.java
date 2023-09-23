package com.hhc.blogs.resp;

import java.util.Date;

public class LikesListByBlogResp {
    private Long id;

    private Long commentatorId;

    private Long blogId;

    private Boolean isLike;

    private Date commentTime;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(Long commentatorId) {
        this.commentatorId = commentatorId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commentatorId=").append(commentatorId);
        sb.append(", blogId=").append(blogId);
        sb.append(", isLike=").append(isLike);
        sb.append(", commentTime=").append(commentTime);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}