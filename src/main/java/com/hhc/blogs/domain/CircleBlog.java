package com.hhc.blogs.domain;

import java.util.Date;

public class CircleBlog {
    private Long id;
    private Long authorId;
    private Long circleId;
    private Date publishTime;
    private String content;
    private Integer voteNum;
    private Integer opposeNum;
    private Integer commentNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }

    public Integer getOpposeNum() {
        return opposeNum;
    }

    public void setOpposeNum(Integer opposeNum) {
        this.opposeNum = opposeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CircleBlog{");
        sb.append("id=").append(id);
        sb.append(", authorId=").append(authorId);
        sb.append(", circleId=").append(circleId);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", content='").append(content).append('\'');
        sb.append(", voteNum=").append(voteNum);
        sb.append(", opposeNum=").append(opposeNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append('}');
        return sb.toString();
    }
}
