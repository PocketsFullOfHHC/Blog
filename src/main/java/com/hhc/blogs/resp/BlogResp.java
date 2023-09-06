package com.hhc.blogs.resp;

import java.util.Date;

public class BlogResp {
    private Long id;

    private Long authorId;

    private Date publishTime;

    private Integer voteNum;

    private Integer opposeNum;

    private Integer commentNum;

    private String content;

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

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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
        sb.append(", id=").append(id);
        sb.append(", authorId=").append(authorId);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", voteNum=").append(voteNum);
        sb.append(", opposeNum=").append(opposeNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}