package com.killer.demo.modules.main.service.impl;/**
 * @author killer
 * @date 2018/12/2 -  11:53
 **/

import com.killer.demo.modules.main.dao.MenuMapper;
import com.killer.demo.modules.main.dao.OperationMapper;
import com.killer.demo.modules.main.dao.RoleMapper;
import com.killer.demo.modules.main.dao.UserMapper;
import com.killer.demo.modules.main.excetpion.*;
import com.killer.demo.modules.main.model.*;
import com.killer.demo.modules.main.service.MainService;
import com.killer.demo.utils.RandomUtils;
import org.apache.jasper.tagplugins.jstl.core.Remove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 *@description
 *@author killer
 *@date 2018/12/02 - 11:53
 */
@Service
public class MainServiceImpl implements MainService {
    private UserMapper userMapper;

    private RoleMapper roleMapper;

    private MenuMapper menuMapper;

    private OperationMapper operationMapper;

    @Autowired
    public MainServiceImpl(UserMapper userMapper, RoleMapper roleMapper, MenuMapper menuMapper, OperationMapper operationMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.operationMapper = operationMapper;
    }

//    在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,
//    加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
    @Transactional(rollbackFor = AddUserException.class)
    @Override
    public void addUser(User user) throws AddUserException {
        // 后台第二次校验，防止插入错误数据，前台不一定使用浏览器
        // 检测用户名是否唯一
        String username = user.getUsername();
        if (userMapper.checkUserNameUnique(username) != null) {
            throw new UserNameRedulicateException("用户名已存在！");
        }

        // 检测密码是否符合要求
        String password = user.getPassword();

        userMapper.insert(user);
    }

    @Transactional(rollbackFor = RemoveUserException.class)
    @Override
    public void removeUser(String username) throws RemoveUserException {
        // 判断用户是否存在，可能会有多个管理员同时删除，会有并发，虽然概率极小
        String uname = userMapper.checkUserNameUnique(username);
        if(uname != null) {
            int result = userMapper.deleteByUsername(username);
            System.out.println("删除返回>>>>>>>>>>>>>>>>>>>" + result);
            if(result != 1) {
                throw new RemoveUserException("删除失败!");
            }
        }
    }

    @Override
    public List<User> getUserAll(String username, String roleId) {
        return userMapper.selectUserAll(username, roleId);
    }

    @Override
    public List<Role> getRolesAll(String rolename) {
        List<Role> rolesAll = roleMapper.getRolesAll(rolename);
        return rolesAll;
    }

    @Transactional(rollbackFor = AddRoleException.class)
    @Override
    public void addRole(Role role) throws AddRoleException {
        String uuid = RandomUtils.uuid();
        role.setId(uuid);
        LocalDateTime now = LocalDateTime.now();
        // 系统默认时区
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = now.atZone(zone).toInstant();
        java.util.Date time = Date.from(instant);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        role.setIntime(simpleDateFormat.format(time));
        role.setUpdatetime(simpleDateFormat.format(time));

        roleMapper.insert(role);
    }

    @Transactional(rollbackFor = RemoveRoleException.class)
    @Override
    public void removeRole(String[] roleIds) throws RemoveRoleException {
        roleMapper.batchDelete(roleIds);
    }

    @Override
    public List<Menu> getfMenusAll() {
        return menuMapper.selectfMenusAll();
    }

    @Override
    public List<Menu> getsMenusAll(String fid) {
       return menuMapper.selectsMenusAll(fid);
    }

    @Override
    public List<Operation> getOperationsAll(String menuId) {
        return operationMapper.selectByMenuId(menuId);
    }

    @Override
    public void addMenu(Menu menu) {
        String uuid = RandomUtils.uuid();
        menu.setId(uuid);

//        LocalDateTime localDateTime = LocalDateTime.now();
//        ZoneId zone = ZoneId.systemDefault();
//        Instant instant = localDateTime.atZone(zone).toInstant();
//        java.util.Date date = Date.from(instant);

        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date date = Date.from(instant);

        menu.setIntime(date);
        menu.setUpdatetime(date);
        menuMapper.addMenu(menu);
    }

    @Override
    public void addOperation(Operation operation) {
        String uuid = RandomUtils.uuid();
        operation.setId(uuid);

        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date date = Date.from(instant);

        operation.setIntime(date);
        operation.setUpdatetime(date);
        int result = operationMapper.insert(operation);
    }

    @Override
    public List<Menu> getSideMenus() {
        List<Menu> menus = menuMapper.selectfMenusAll();

        for (Menu menu : menus) {
            List<Menu> smenus = menuMapper.selectsMenusAll(menu.getId());
            menu.setList(smenus);
            for (Menu smenu : smenus) {
                if(smenu.isHasChildren()) {
                    smenu.setList(menuMapper.selectsMenusAll(smenu.getId()));
                }
            }
        }

        return menus;
    }

    @Override
    public List<RoleMenu> getAuthMenu(String id) {
        return null;
    }
}
