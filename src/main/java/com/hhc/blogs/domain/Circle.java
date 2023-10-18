package com.hhc.blogs.domain;

import java.util.Date;

public class Circle {
    private Long id;
    private String circleName;
    private String intro;
    private Long managerId;
    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Circle{");
        sb.append("id=").append(id);
        sb.append(", circleName='").append(circleName).append('\'');
        sb.append(", intro='").append(intro).append('\'');
        sb.append(", managerId=").append(managerId);
        sb.append(", createdTime=").append(createdTime);
        sb.append('}');
        return sb.toString();
    }
}
