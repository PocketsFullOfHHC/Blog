package com.hhc.blogs.exception;

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("用户名已存在"),
    LOGIN_USER_ERROR("用户不存在或密码错误"),
    VOTE_REPEAT("您已点赞或点踩过"),
    FOLLOW_REPEAT("您已关注过"),
    COLLECT_REPEAT("您已收藏过"),
    CIRCLE_NAME_EXIST("部落名已存在"),
    JOINED_CIRCLE_REPEAT("您已加入该部落"),
    JOINED_CIRCLE_ERROR("创建者不能加入部落")
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
