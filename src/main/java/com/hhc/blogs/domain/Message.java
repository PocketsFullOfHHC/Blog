package com.hhc.blogs.domain;

import java.util.Date;

public class Message {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("id=").append(id);
        sb.append(", senderId=").append(senderId);
        sb.append(", receiverId=").append(receiverId);
        sb.append(", content='").append(content).append('\'');
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}