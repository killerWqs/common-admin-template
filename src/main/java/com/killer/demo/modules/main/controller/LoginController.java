package com.killer.demo.modules.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.modules.main.model.User;
import com.killer.demo.modules.main.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

//import com.killer.demo.modules.main.dao.TestMapper;
//import com.killer.demo.modules.main.model.Test;

/**
 * TODO...
 * 返回字符串返回的却是一个视图
 * @author wqs
 * @date 2018-11-15 15:30
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper;

//    private TestMapper testMapper;

    private LoginService loginService;

    @Autowired
    public LoginController(ObjectMapper objectMapper, LoginService loginService) {
//        this.testMapper = testMapper;
        this.objectMapper = objectMapper;
        this.loginService = loginService;
    }

    @GetMapping(value = "")
    public ModelAndView index(HttpServletResponse response) throws IOException {
        return new ModelAndView("front/html/index");
    }

    // 使用spring security来验证 所以没必要有这个路径匹配，不对，登陆完之后需要开启websocket长连接
    // 不需要验证 @validated
    @PostMapping("admin/login")
    public void adminLogin(User user) {

    }

    @RequestMapping("yiqihui/qqlogin")
    @ResponseBody
    public String qqLogin(@RequestParam(value = "code", required = false) String code,
                        @RequestParam(value = "state", required = false) String state, @RequestParam(value = "msg", required = false) String msg,
                        HttpServletRequest request, HttpServletResponse resp) throws IOException {
//        https://graph.qq.com/oauth2.0/token
//        成功登录
        logger.info("请求url：" + request.getRequestURI() + "?" + request.getQueryString());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            logger.info("parameter:" + parameterNames.nextElement());
        }

//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<HashMap> accessToken = restTemplate.getForEntity("https://graph.qq.com/oauth2.0/token", HashMap.class, params);
        String accessToken = loginService.fetchAccessToken(resp, code);

        String result = loginService.fetchOpenId(resp, accessToken);

//      应该将openid存入到数据库中，返回一个token，使用token获取用户信息。
        return result;
    }

    @RequestMapping("qqLoginSuccess")
    public void qqLoginSuccess(HttpServletRequest request, HttpServletResponse response) {
        logger.info("qqLoginSuccess");
    }

    @GetMapping("test")
    public ModelAndView test(@RequestHeader("Accept-Language") String acceptLanguage, Model model, @RequestParam int value) {
        model.addAttribute("acceptLanguage", acceptLanguage);
        return new ModelAndView("test1");
    }

    @PostMapping(value = "properties", consumes = "text/properties", produces = "text/properties")
    @ResponseBody //表明返回值是responsebody中的值 跟选择converter无关
    public Properties properties(@RequestBody Properties properties) {
        System.out.println(properties.get("username"));
//        return properties.get("username").toString();
        return properties;
    }

//    @GetMapping(value = "test1")
//    @ResponseBody
//    public Test test() {
//        List<Test> tests = testMapper.selectAll();
//        return tests.get(0);
//    }

    public static void main(String[] args) {
        System.out.println(Integer.valueOf("-010"));
    }
}
