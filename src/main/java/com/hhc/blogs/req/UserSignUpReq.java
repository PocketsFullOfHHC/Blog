package com.hhc.blogs.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserSignUpReq {
    @NotEmpty(message = "【用户名】不能为空")
    String username;
    @NotEmpty(message = "【昵称】不能为空")
    String name;
    @NotEmpty(message = "【密码】不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "【密码】至少包含数字和英文")
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}

