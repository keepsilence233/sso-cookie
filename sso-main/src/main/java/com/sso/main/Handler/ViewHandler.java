package com.sso.main.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/view")
public class ViewHandler {

    @Autowired
    private RestTemplate restTemplate;

    //获取登陆Cookie信息的Url信息
    private final String LOGIN_INFO_ADDRESS = "http://login.codeshop.com:9000/login/info?token=";

    @RequestMapping(value = "/main")
    public String toMain(@CookieValue(required = false,value = "TOKEN") Cookie cookie,
                         HttpSession session){
        if(cookie != null){
            String token = cookie.getValue();
            if(!StringUtils.isEmpty(token)){
                Map result = restTemplate.getForObject(LOGIN_INFO_ADDRESS + token, Map.class);
                session.setAttribute("LoginUser",result);
            }
        }
        return "index";
    }
}
