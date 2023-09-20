package com.hhc.blogs.req;

public class UserInfoReq {
    private Long id;

    private String selfIntro;

    private Integer age;

    private String gender;

    private Boolean status;

    private String circles;

    private String managedCircles;

    private String tags;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelfIntro() {
        return selfIntro;
    }

    public void setSelfIntro(String selfIntro) {
        this.selfIntro = selfIntro;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCircles() {
        return circles;
    }

    public void setCircles(String circles) {
        this.circles = circles;
    }

    public String getManagedCircles() {
        return managedCircles;
    }

    public void setManagedCircles(String managedCircles) {
        this.managedCircles = managedCircles;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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
        sb.append(", selfIntro=").append(selfIntro);
        sb.append(", age=").append(age);
        sb.append(", gender=").append(gender);
        sb.append(", status=").append(status);
        sb.append(", circles=").append(circles);
        sb.append(", managedCircles=").append(managedCircles);
        sb.append(", tags=").append(tags);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}