package com.killer.demo.modules.main.service;

import com.killer.demo.modules.main.excetpion.AddUserException;
import com.killer.demo.modules.main.model.User;

import java.util.List;

/**
 * @author killer
 * @date 2018/11/25 -  12:40
 **/
public interface MainService {
    void addUser(User user) throws AddUserException;

    List<User> getUserAll(String username, String roleId);
}
