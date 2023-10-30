package com.hhc.blogs.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class StatisticResp {
    // 日期的格式化：以便显示到前端趋势图的横轴上
    @JsonFormat(pattern="MM-dd", timezone = "GMT+8")
    private Date date;

    private int voteNum;

    private int commentNum;

    private int voteIncrease;

    private int commentIncrease;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getVoteIncrease() {
        return voteIncrease;
    }

    public void setVoteIncrease(int voteIncrease) {
        this.voteIncrease = voteIncrease;
    }

    public int getCommentIncrease() {
        return commentIncrease;
    }

    public void setCommentIncrease(int commentIncrease) {
        this.commentIncrease = commentIncrease;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatisticResp{");
        sb.append("date=").append(date);
        sb.append(", voteNum=").append(voteNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", voteIncrease=").append(voteIncrease);
        sb.append(", commentIncrease=").append(commentIncrease);
        sb.append('}');
        return sb.toString();
    }
}


