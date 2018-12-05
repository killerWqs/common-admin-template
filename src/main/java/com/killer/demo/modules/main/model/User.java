package com.killer.demo.modules.main.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class User {
    private String id;

    // 用户名必须为手机号
    @NotNull
    @Size(min = 11, max = 11)
    private String username;

    // 密码为8到20位之间，后期添加正则校验，必须同时存在大小写字母，以及数字
    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    // 昵称为3到10位
    @NotNull
    @Size(min = 3, max = 10)
    private String nickname;

    @NotNull
    private String roleId;

    private Date intime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}