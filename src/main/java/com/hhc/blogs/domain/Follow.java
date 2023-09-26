package com.hhc.blogs.domain;

import java.util.Date;

public class Follow {
    private Long id;

    private Long likeId;

    private Long belikedId;

    private Date followTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getBelikedId() {
        return belikedId;
    }

    public void setBelikedId(Long belikedId) {
        this.belikedId = belikedId;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }

    public Follow(Long id, Long likeId, Long belikedId, Date followTime) {
        this.id = id;
        this.likeId = likeId;
        this.belikedId = belikedId;
        this.followTime = followTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", likeId=").append(likeId);
        sb.append(", belikedId=").append(belikedId);
        sb.append(", followTime=").append(followTime);
        sb.append("]");
        return sb.toString();
    }
}