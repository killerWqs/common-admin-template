package com.killer.demo.modules.main.service;

import com.killer.demo.modules.main.excetpion.AddRoleException;
import com.killer.demo.modules.main.excetpion.AddUserException;
import com.killer.demo.modules.main.excetpion.RemoveRoleException;
import com.killer.demo.modules.main.excetpion.RemoveUserException;
import com.killer.demo.modules.main.model.Menu;
import com.killer.demo.modules.main.model.Role;
import com.killer.demo.modules.main.model.User;

import java.util.List;

/**
 * @author killer
 * @date 2018/11/25 -  12:40
 **/
public interface MainService {
    /** 添加用戶*/
    void addUser(User user) throws AddUserException;

    /** 刪除用戶*/
    void removeUser(String username) throws RemoveUserException;

    /** 获取所有用户*/
    List<User> getUserAll(String username, String roleId);

    /** 获取所有的角色*/
    List<Role> getRolesAll(String rolename);

    /** 添加角色*/
    void addRole(Role role) throws AddRoleException;

    /** 移除角色*/
    void removeRole(String[] roleIds) throws RemoveRoleException;

    /** 获取所有一级菜单*/
    List<Menu> getfMenusAll();

    List<Menu> getsMenusAll(String fid);
}
