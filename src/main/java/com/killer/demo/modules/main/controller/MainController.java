package com.killer.demo.modules.main.controller;/**
 * @author killer
 * @date 2018/11/25 -  12:37
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.modules.main.excetpion.AddRoleException;
import com.killer.demo.modules.main.excetpion.AddUserException;
import com.killer.demo.modules.main.excetpion.RemoveRoleException;
import com.killer.demo.modules.main.excetpion.RemoveUserException;
import com.killer.demo.modules.main.model.Menu;
import com.killer.demo.modules.main.model.Role;
import com.killer.demo.modules.main.model.User;
import com.killer.demo.modules.main.service.LoginService;
import com.killer.demo.modules.main.service.MainService;
import com.killer.demo.utils.RandomUtils;
import com.killer.demo.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    // 在restcontroller下返回view也是可以的
    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("views/index");
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

        String uuid = RandomUtils.uuid();
        user.setId(uuid);
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.toInstant(ZoneOffset.of("+8"));
        java.util.Date intime = Date.from(instant);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        user.setIntime(simpleDateFormat.format(intime));
        user.setUpdatetime(simpleDateFormat.format(intime));

        // 添加用户
        mainService.addUser(user);
    }

    @DeleteMapping("user")
    public void removeUser(@RequestBody String usernames) throws RemoveUserException, IOException {
        System.out.println(usernames);
        String[] strings = objectMapper.readValue(usernames, String[].class);

        for (String string : strings) {
            mainService.removeUser(string);
        }
    }

    /**
     * @description 查找所有用户，并且可以通过username，进行模糊查询，roleId进行精确查询
     * @param username
     * @param roleId
     * @return
     */
    @GetMapping("alluser")
    public Response<List<User>> getUserAll(@RequestParam(value = "username", required = false) String username,
                                    @RequestParam(value = "roleId", required = false) String roleId) {
        List<User> userAll = mainService.getUserAll(username, roleId);

        for (User user : userAll) {
            System.out.println(user.getIntime());
        }
//        输出Fri Dec 07 00:00:00 CST 2018 也就是并没有转错

        Response<List<User>> listResponse = new Response<>();
        if(userAll != null && userAll.size() != 0) {
            listResponse.setData(userAll);
        }
        // 成功返回code：0
        listResponse.setCode(0);
        return listResponse;
    }

    @GetMapping("allroles")
    public Response<List<Role>> getAllRoles(@RequestParam(value = "rolename", required = false) String rolename) {
        List<Role> rolesAll = mainService.getRolesAll(rolename);
        Response<List<Role>> listResponse = new Response<>();
        listResponse.setData(rolesAll);
        return listResponse;
    }

    @PostMapping("role")
    public void addRole(@Validated Role role, BindingResult result) throws AddRoleException {
        if(result.hasErrors()) {
            throw new AddRoleException("添加角色失败");
        }

        mainService.addRole(role);
    }

    @DeleteMapping("role")
    public void removeRole(@RequestBody String ids) throws IOException, RemoveRoleException {
        String[] strings = objectMapper.readValue(ids, String[].class);
        mainService.removeRole(strings);
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

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.toInstant(ZoneOffset.of("+8"));
        java.util.Date intime = Date.from(instant);
        System.out.println(intime);
    }

    @GetMapping("getSessionId")
    public String getSessionId(HttpServletRequest request) {
        request.getSession().setAttribute("name", "lalala");
        return request.getSession().getId();
    }
}
