package com.killer.demo.modules.main.controller;/**
 * @author killer
 * @date 2018/11/25 -  12:37
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.modules.main.excetpion.AddRoleException;
import com.killer.demo.modules.main.excetpion.AddUserException;
import com.killer.demo.modules.main.excetpion.RemoveRoleException;
import com.killer.demo.modules.main.excetpion.RemoveUserException;
import com.killer.demo.modules.main.model.*;
import com.killer.demo.modules.main.service.IMService;
import com.killer.demo.modules.main.service.LoginService;
import com.killer.demo.modules.main.service.MainService;
import com.killer.demo.utils.RandomUtils;
import com.killer.demo.utils.Response;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author killer
 * @description 用户管理，角色管理，权限管理
 * @date 2018/11/25 - 12:37
 */
@Api(description = "后台管理员模块", tags = "User Admin")
@RestController
@RefreshScope
@RequestMapping("admin")
public class MainController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private LoginService loginService;

    private MainService mainService;

    private ObjectMapper objectMapper;

    private IMService imService;

    @Autowired
    public MainController(LoginService loginService, MainService mainService, IMService imService, ObjectMapper objectMapper) {
        this.mainService = mainService;
        this.loginService = loginService;
        this.objectMapper = objectMapper;
        this.imService = imService;
    }

    // 在restcontroller下返回view也是可以的 因为重定向请求所以使用requestmapping
    @RequestMapping()
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
     * @param username
     * @param roleId
     * @return
     * @description 查找所有用户，并且可以通过username，进行模糊查询，roleId进行精确查询
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
        if (userAll != null && userAll.size() != 0) {
            listResponse.setData(userAll);
        }
        // 成功返回code：0
        listResponse.setCode(0);
        return listResponse;
    }

    /**
     * 获取所有角色
     * @param rolename
     * @return
     */
    @GetMapping("allroles")
    public Response<List<Role>> getAllRoles(@RequestParam(value = "rolename", required = false) String rolename) {
        List<Role> rolesAll = mainService.getRolesAll(rolename);
        Response<List<Role>> listResponse = new Response<>();
        listResponse.setData(rolesAll);
        return listResponse;
    }

    @PostMapping("role")
    public void addRole(@Validated Role role, BindingResult result) throws AddRoleException {
        if (result.hasErrors()) {
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
    public Response<List<Menu>> getfMenusAll(@SessionAttribute(value = "user", required = false) User user) throws JsonProcessingException {
//        return new ModelAndView("main/menus");
        logger.info("execute");
//        String id = user.getId();

        List<Menu> menus = mainService.getfMenusAll();

        Response<List<Menu>> menuResponse = new Response<>();
        menuResponse.setData(menus);
        menuResponse.setCode(0);
        return menuResponse;
    }

    /**
     * 为什么要使用requestparam 因为他的功能并不仅仅是获取参数，他还有校验功能
     * @param fid 获取底层菜单
     */
    @GetMapping("smenus")
    public Response<List<Menu>> getsMenusAll(@RequestParam("fid") String fid) {
        List<Menu> menus = mainService.getsMenusAll(fid);
        Response<List<Menu>> listResponse = new Response<>();
        listResponse.setData(menus);
        return listResponse;
    }

    @PostMapping("menu")
    public void addMenu(@Validated Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            // 检查参数抛出异常
        }

        mainService.addMenu(menu);
    }

    /**
     * 获取菜单下面的操作,以及授权情况
     * @param menuId 菜单id
     */
    @GetMapping("operations")
    public Response<List<Operation>> getOperationsAll(@RequestParam("menuId") String menuId) {
        List<Operation> operations = mainService.getOperationsAll(menuId);
        Response<List<Operation>> listResponse = new Response<>();
        listResponse.setData(operations);
        return listResponse;
    }

    /**
     * 菜单添加操作
     * @param operation 操作
     */
    @PostMapping("operation")
    public void addOperation(Operation operation) {
        mainService.addOperation(operation);
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
        // 中国在东八区
        Instant instant = now.toInstant(ZoneOffset.of("+8"));
        java.util.Date intime = Date.from(instant);
        System.out.println(intime);

        String string = "{\"authMenus\":[\"1\"],\"cancelMenuAuth\":[],\"operaAuth\":[],\"cancelOperaAuth\":[]}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map map = objectMapper.readValue(string, new TypeReference<Map<String, String[]>>(){});
            for (Object o : map.keySet()) {
                System.out.println(map.get(o.toString()).getClass());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String text = "[1]";
        try {
            String[] strings = objectMapper.readValue(text, String[].class);
            System.out.println(Arrays.toString(strings));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 有登陆状态下的测试真的挺难得

    /**
     * 获取侧边授权菜单
     * @param request
     * @param roleId 角色id
     * @return
     */
    @GetMapping("sideMenus")
    public Response<List<Menu>> getSideMenus(HttpServletRequest request, @RequestParam("roleId") String roleId) {
        Response<List<Menu>> listResponse = new Response<>();
        List<Menu> sideMenus = mainService.getSideMenus();

        // 处理是否授权
        // 获取当前登录用户
//        HttpSession session = request.getSession();
//        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
//        UserDetails user = (UserDetails)securityContext.getAuthentication().getDetails();
//        String id = String.valueOf(user.getUsername());

        List<Menu> authMenus = mainService.getAuthMenu(roleId, sideMenus);

        listResponse.setData(authMenus);
        return listResponse;
    }

    /**
     * 处理授权请求
     * @param request 用来获取非pojo数据,传送过来的必定是json字符串
     * @return
     */
    @PostMapping("/authenticate")
    public Response<String> authenticate(HttpServletRequest request, @RequestParam("authMenus") String authMenus, @RequestParam("cancelMenuAuth") String cancelMenuAuth
            , @RequestParam("operaAuth") String operaAuth, @RequestParam("cancelOperaAuth") String cancelOperaAuth) throws IOException {
//        ServletInputStream inputStream = request.getInputStream();
//        InputStreamReader reader = new InputStreamReader(inputStream);
//        int readLength = reader.read();
//        char[] container = new char[readLength];
//        reader.read(container, 0, readLength);
//        // 转换数据类型最好使用valueOf
//        String data = String.valueOf(container);

        // 终于明白为什么要转换为pojo了，处理起来真的难
        // spring无法自动转换数组类型
        String[] authMenusArray = objectMapper.readValue(authMenus, String[].class);
        String[] cancelMenuAuthArray = objectMapper.readValue(cancelMenuAuth, String[].class);
        String[] operaAuthArray = objectMapper.readValue(operaAuth, String[].class);
        String[] cancelOperaAuthArray = objectMapper.readValue(cancelOperaAuth, String[].class);

        return null;
    }

    @Value("${name}")
    private String name;

    @GetMapping("configTest")
    public String configTest() {
        return "Hello" + name + "!";
    }

    @GetMapping("feignTest")
    public String test() {
        return imService.feignTest();
    }
}
