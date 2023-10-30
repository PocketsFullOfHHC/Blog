package com.hhc.blogs.domain;

import java.util.Date;

public class BlogSnapshot {
    private Long id;

    private Long blogId;

    private Date date;

    private Integer voteNum;

    private Integer commentNum;

    private Integer voteIncrease;

    private Integer commentIncrease;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getVoteIncrease() {
        return voteIncrease;
    }

    public void setVoteIncrease(Integer voteIncrease) {
        this.voteIncrease = voteIncrease;
    }

    public Integer getCommentIncrease() {
        return commentIncrease;
    }

    public void setCommentIncrease(Integer commentIncrease) {
        this.commentIncrease = commentIncrease;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", blogId=").append(blogId);
        sb.append(", date=").append(date);
        sb.append(", voteNum=").append(voteNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", voteIncrease=").append(voteIncrease);
        sb.append(", commentIncrease=").append(commentIncrease);
        sb.append("]");
        return sb.toString();
    }
}