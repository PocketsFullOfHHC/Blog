package com.hhc.blogs.req;

import javax.validation.constraints.NotEmpty;

public class UserLoginReq {
    @NotEmpty(message = "【用户名】不能为空")
    String username;
    @NotEmpty(message = "【密码】不能为空")
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", username=").append(username);
        sb.append("]");
        return sb.toString();
    }
}

