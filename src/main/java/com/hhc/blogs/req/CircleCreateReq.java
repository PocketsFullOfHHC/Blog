package com.hhc.blogs.req;

public class CircleCreateReq {
    private String circleName;
    private String intro;
    private Long managerId;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Circle{");
        sb.append(", circleName='").append(circleName).append('\'');
        sb.append(", intro='").append(intro).append('\'');
        sb.append(", managerId=").append(managerId);
        sb.append('}');
        return sb.toString();
    }
}
