package com.killer.demo.modules.main.service;

import com.killer.demo.modules.main.excetpion.AddUserException;
import com.killer.demo.modules.main.excetpion.RemoveUserException;
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
}
