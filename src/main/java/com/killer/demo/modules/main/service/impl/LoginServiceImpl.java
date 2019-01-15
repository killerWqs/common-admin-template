package com.killer.demo.modules.main.service.impl;/**
 * @author killer
 * @date 2018/11/17 -  20:43
 **/

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.modules.main.dao.MenuMapper;
import com.killer.demo.modules.main.dao.RoleMenuMapper;
import com.killer.demo.modules.main.model.Menu;
import com.killer.demo.modules.main.service.LoginService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *@description
 * @advice 可以提取区出一个函数
 *@author killer
 *@date 2018/11/17 - 20:43
 */
@Service
public class LoginServiceImpl implements LoginService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String QQ_LOGIN_ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    private static final String QQ_LOGIN_OPENID_URL = "https://graph.qq.com/oauth2.0/me";

    private ObjectMapper objectMapper;

    private RoleMenuMapper roleMenuMapper;
    @Autowired
    public LoginServiceImpl(ObjectMapper objectMapper, RoleMenuMapper roleMenuMapper) {
        this.objectMapper = objectMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    // TODO 获取accesstoken
    @Override
    public String fetchAccessToken(HttpServletResponse resp, String code) throws IOException {
        Map<String, String> params = new HashMap<>(5);
        params.put("grant_type", "authorization_code");
        params.put("client_id", "101518553");
        params.put("client_secret", "655ecc0dfda0b36ec98a49e4f7fcf831");
        params.put("code", code);
        params.put("redirect_uri", "http://localhost/qqLoginSuccess");
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//        get请求是无法携带参数的
        StringBuilder handledUrl = new StringBuilder(QQ_LOGIN_ACCESS_TOKEN_URL + "?");
        String resultUrl = null;
        params.forEach((key, value) -> {
            handledUrl.append(key + "=" + value + "&");
        });

        if(handledUrl.toString().endsWith("&")) {
            resultUrl = handledUrl.toString().substring(0, handledUrl.length() - 1);
        }

        HttpGet httpGet = new HttpGet(resultUrl);

        String result = closeableHttpClient.execute(httpGet, new BasicResponseHandler() {
            @Override
            public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
                HttpEntity entity = response.getEntity();
                InputStreamReader content = new InputStreamReader(entity.getContent());
                char[] buffer = new char[500];
                content.read(buffer);

                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    logger.info("获取失败");
                    resp.setContentType("text/html");
                    resp.getWriter().write(buffer);
                    return null;
                }

                String res = String.valueOf(buffer);
                logger.info("获取成功,返回数据：" + res);
                return res;
            }
        });

        String[] strings = StringUtils.delimitedListToStringArray(result, "&");
        String accessToken = null;

        for(String string : strings) {
            String[] strings1 = StringUtils.delimitedListToStringArray(string, "=");
            if(strings1[0].equals("access_token")) {
                accessToken = strings1[1];
                break;
            }
        }

        return accessToken;
    }

    @Override
    // TODO 获取openid
    public String fetchOpenId(HttpServletResponse resp, String token) throws IOException {
        Map<String, String> params = new HashMap<>(5);
        params.put("access_token", token);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//        get请求是无法携带参数的
        StringBuilder handledUrl = new StringBuilder(QQ_LOGIN_OPENID_URL + "?");
        params.forEach((key, value) -> {
            handledUrl.append(key + "=" + value);
        });

        HttpGet httpGet = new HttpGet(handledUrl.toString());

        String result = closeableHttpClient.execute(httpGet, new BasicResponseHandler() {
            @Override
            public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
                HttpEntity entity = response.getEntity();
                InputStreamReader content = new InputStreamReader(entity.getContent());
                char[] buffer = new char[500];
                content.read(buffer);

                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    logger.info("获取失败");
                    resp.setContentType("text/html");
                    resp.getWriter().write(buffer);
                    return null;
                }

                String res = String.valueOf(buffer);
                logger.info("获取成功,返回数据：" + res);
                return res;
            }
        });

//        Map map = objectMapper.readValue(result, Map.class);
//        Object openid = map.get("openid");

        return result;
    }

    @Override
    public List<Menu> userMenuList(String roleId) {
        List<Menu> menus = roleMenuMapper.selectMenusByUserId(roleId);

        HashMap<Integer, List<Menu>> resolvedMenus = new HashMap<>();

        menus.forEach((menu) -> {
            if(resolvedMenus.get(menu.getLevel()) == null) {
                ArrayList<Menu> list = new ArrayList<>();
                list.add(menu);
                resolvedMenus.put(menu.getLevel(), list);
            }
        });

        resolvedMenus.forEach((key, value) -> {
            List<Menu> childList = resolvedMenus.get(key + 1);
            if( childList == null) {
                return;
            }

            childList.forEach((menu) -> {
                value.forEach(pmenu -> {
                    if(menu.getFid().equals(pmenu.getId())) {
                        pmenu.addList(menu);
                    }
                });
            });
        });

        return resolvedMenus.get(0);
    }

    public static void main(String[] args) {
        String a = "MDFlYzhmMTYtNmFkZi00OTdhLWJjM2YtMDU5MDkzYmMxMzM3";
        Base64.Decoder decoder = Base64.getDecoder();
        System.out.println(new String(decoder.decode(a.getBytes())));
//        2aa6eae2-feb4-4d67-8886-cf5daadc13f9
        String b = "01ec8f16-6adf-497a-bc3f-059093bc1337";
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.println(encoder.encode(b.getBytes()).toString());
    }
}
