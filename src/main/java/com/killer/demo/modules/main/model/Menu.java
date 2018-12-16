package com.killer.demo.modules.main.model;

import java.util.Date;
import java.util.List;

public class Menu {
    private String id;

    private String name;

    private String title;

    private String icon;

    private String jump;

    private String fid;

    private String userId;

    private boolean hasChildren;

    private int level;

    private int order;

    private Date intime;

    private Date updatetime;

    private List<Menu> list;

    public List<Menu> getList() {
        return list;
    }

    public void addList(Menu menu) {
        this.list.add(menu);
    }

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

        this.title = this.name;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getJump() {
        return jump;
    }

    public void setJump(String url) {
        this.jump = url == null ? null : url.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}