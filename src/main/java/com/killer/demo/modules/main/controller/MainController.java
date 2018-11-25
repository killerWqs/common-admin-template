package com.killer.demo.modules.main.controller;/**
 * @author killer
 * @date 2018/11/25 -  12:37
 **/

import com.killer.demo.modules.main.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@description 用户管理，角色管理，权限管理
 *@author killer
 *@date 2018/11/25 - 12:37
 */
@RestController
@RequestMapping("/main")
public class MainController {
    @GetMapping("")
    public void addUser(User user) {
        
    }
}
