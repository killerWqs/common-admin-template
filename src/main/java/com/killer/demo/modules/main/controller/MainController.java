package com.killer.demo.modules.main.controller;/**
 * @author killer
 * @date 2018/11/25 -  12:37
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.modules.main.model.Menu;
import com.killer.demo.modules.main.model.User;
import com.killer.demo.modules.main.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@description 用户管理，角色管理，权限管理
 *@author killer
 *@date 2018/11/25 - 12:37
 */
@RestController
@RequestMapping("admin")
public class MainController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private LoginService loginService;

    private ObjectMapper objectMapper;

    @Autowired
    public MainController(LoginService loginService, ObjectMapper objectMapper) {
        this.loginService = loginService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("menus")
    @ResponseBody
    public List<Menu> userMenuList(@SessionAttribute(value = "user", required = false) User user) throws JsonProcessingException {
//        return new ModelAndView("main/menus");
        logger.info("execute");
//        String id = user.getId();

        List<Menu> menus = loginService.userMenuList("1");

        System.out.println(objectMapper.writeValueAsString(menus));
        return menus;
    }

//    @GetMapping("")
//    public void addUser(User user) {
//
//    }
}
