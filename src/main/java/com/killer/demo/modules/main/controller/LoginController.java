package com.killer.demo.modules.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.modules.main.model.Menu;
import com.killer.demo.modules.main.model.User;
import com.killer.demo.modules.main.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

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

    private LoginService loginService;

    @Autowired
    public LoginController(ObjectMapper objectMapper, LoginService loginService) {
        this.objectMapper = objectMapper;
        this.loginService = loginService;
    }

    @GetMapping(value = "")
    public ModelAndView login(HttpServletResponse response) throws IOException {
        return new ModelAndView("login");
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
}
