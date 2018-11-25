package com.killer.demo.modules.main.service;/**
 * @author killer
 * @date 2018/11/17 -  20:43
 **/

import com.killer.demo.modules.main.model.Menu;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *@description
 *@author killer
 *@date 2018/11/17 - 20:43
 */
public interface LoginService {
    String fetchAccessToken(HttpServletResponse resp, String code) throws IOException;

    // TODO 获取openid
    String fetchOpenId(HttpServletResponse resp, String token) throws IOException;

    List<Menu> userMenuList(String userId);
}
