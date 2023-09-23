package com.hhc.blogs.req;

public class LikesSaveReq {

    private Long commentatorId;

    private Long blogId;

    private Boolean isLike;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentatorId=").append(commentatorId);
        sb.append(", blogId=").append(blogId);
        sb.append(", isLike=").append(isLike);
        sb.append("]");
        return sb.toString();
    }
}