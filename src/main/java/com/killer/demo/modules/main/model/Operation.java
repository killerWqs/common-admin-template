package com.killer.demo.modules.main.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Operation {
    private String id;

    private String name;

    private String invokeFunc;

    private Byte order;

    private String menuId;

    private Boolean Authenticated = false;

    public Boolean getAuthenticated() {
        return Authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        Authenticated = authenticated;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date intime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getInvokeFunc() {
        return invokeFunc;
    }

    public void setInvokeFunc(String invokeFunc) {
        this.invokeFunc = invokeFunc == null ? null : invokeFunc.trim();
    }

    public Byte getOrder() {
        return order;
    }

    public void setOrder(Byte order) {
        this.order = order;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
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