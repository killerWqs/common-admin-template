package com.killer.demo.modules.main.controller;/**
 * @author killer
 * @date 2018/11/25 -  12:37
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.modules.main.excetpion.AddUserException;
import com.killer.demo.modules.main.model.Menu;
import com.killer.demo.modules.main.model.User;
import com.killer.demo.modules.main.service.LoginService;
import com.killer.demo.modules.main.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Properties;

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

    private MainService mainService;

    private ObjectMapper objectMapper;

    @Autowired
    public MainController(LoginService loginService, MainService mainService, ObjectMapper objectMapper) {
        this.mainService = mainService;
        this.loginService = loginService;
        this.objectMapper = objectMapper;
    }

    // TODO 添加用户角色
//    @AssertFalse 校验false
//            @AssertTrue 校验true
//    @DecimalMax(value=,inclusive=) 小于等于value，
//    inclusive=true,是小于等于
//    @DecimalMin(value=,inclusive=) 与上类似
//    @Max(value=) 小于等于value
//    @Min(value=) 大于等于value
//    @NotNull  检查Null
//    @Past  检查日期
//    @Pattern(regex=,flag=)  正则
//    @Size(min=, max=)  字符串，集合，map限制大小
//    @Validate 对po实体类进行校验 @Validated 是对 @Validate的增强 提供了分组功能
    @PostMapping("user")
    public void addUser(@Validated User user, BindingResult result) throws AddUserException {
        // 异常处理
        if (result.hasErrors()) {
            throw new AddUserException(result.getFieldError().getDefaultMessage());
        }

        // 添加用户
        mainService.addUser(user);
    }

    /**
     * @description 查找所有用户，并且可以通过username，进行模糊查询，roleId进行精确查询
     * @param username
     * @param roleId
     * @return
     */
    @GetMapping("alluser")
    public List<User> getUserAll(@RequestParam("username") String username, @RequestParam("roleId") String roleId) {
        List<User> userAll = mainService.getUserAll(username, roleId);
        return userAll;
    }

    @GetMapping("menus")
    public List<Menu> userMenuList(@SessionAttribute(value = "user", required = false) User user) throws JsonProcessingException {
//        return new ModelAndView("main/menus");
        logger.info("execute");
//        String id = user.getId();

        List<Menu> menus = loginService.userMenuList("1");

        System.out.println(objectMapper.writeValueAsString(menus));
        return menus;
    }

    @PostMapping(value = "properties", consumes = "text/properties;charset=utf-8",// 过滤媒体类型 content-type
            produces = "text/properties;charset=utf-8")
    public void addUser(@RequestBody Properties properties) {

        System.out.println(properties.get("username"));
    }

    // TODO 用来测试跨域请求
    @CrossOrigin("localhost")
    @GetMapping("cors/resources")
    public String testCorsRequest() {
        return "Hello my cors request";
    }
}
