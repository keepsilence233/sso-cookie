package com.sso.login.handler;

import com.sso.login.pojo.User;
import com.sso.login.util.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "view")
public class ViewHandler {

    @RequestMapping(value = "/login")
    public String toLogin(@RequestParam(required = false, defaultValue = "") String target,
                          HttpSession session,
                          @CookieValue(required = false,value = "TOKEN") Cookie cookie) {
        if(StringUtils.isEmpty(target)){
            target = "http://www.codeshop.com:9010";
        }
        if(cookie != null){
            /**
             * 取出Cookie的值，并从LoginCacheUtil中取出User
             * 如果User不为空，说明用户已经登陆，跳转到目标页面
             */
            String token = cookie.getValue();
            User user = LoginCacheUtil.LoginUser.get(token);
            if(user != null){
                return "redirect:" + target;
            }
        }
        session.setAttribute("target",target);
        return "Login";
    }
}
