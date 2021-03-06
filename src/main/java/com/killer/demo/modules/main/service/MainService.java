package com.killer.demo.modules.main.service;

import com.killer.demo.modules.main.excetpion.AddRoleException;
import com.killer.demo.modules.main.excetpion.AddUserException;
import com.killer.demo.modules.main.excetpion.RemoveRoleException;
import com.killer.demo.modules.main.excetpion.RemoveUserException;
import com.killer.demo.modules.main.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     *  获取所有二级菜单
     * @param fid
     * @return
     */
    List<Menu> getsMenusAll(String fid);

    /**
     * 获取所有操作
     * @param menuId
     * @param roleId
     * @return
     */
    List<Operation> getOperationsAll(String menuId, String roleId);

    /**
     * 添加菜单
     * @param menu
     */
    void addMenu(Menu menu);

    /**
     * 添加操作
     * @param operation
     */
    void addOperation(Operation operation);

    /**
     * 获取所有侧边菜单
     * @return
     */
    List<Menu> getSideMenus();

    List<Menu> getAuthMenu(String id, List<Menu> sideMenus);

    void modifyMenu(Menu menu);

    void authenticate(String roleId, String[] menuAuth, String[] cancelMenuAuth, String[] operaAuth, String[] cancelOperaAuth);

    /**
     * 删除菜单
     * @param ids
     */
    void deleteMenu(String[] ids);
}
