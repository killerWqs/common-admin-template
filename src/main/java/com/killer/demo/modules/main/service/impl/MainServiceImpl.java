package com.killer.demo.modules.main.service.impl;/**
 * @author killer
 * @date 2018/12/2 -  11:53
 **/

import com.killer.demo.modules.main.dao.RoleMapper;
import com.killer.demo.modules.main.dao.UserMapper;
import com.killer.demo.modules.main.excetpion.AddUserException;
import com.killer.demo.modules.main.excetpion.RemoveUserException;
import com.killer.demo.modules.main.excetpion.UserNameRedulicateException;
import com.killer.demo.modules.main.model.Role;
import com.killer.demo.modules.main.model.User;
import com.killer.demo.modules.main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    @Autowired
    public MainServiceImpl(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

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

        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        user.setIntime(Date.from(now.atZone(zoneId).toInstant()));
        user.setUpdatetime(Date.from(now.atZone(zoneId).toInstant()));

        userMapper.insert(user);
    }

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
}
