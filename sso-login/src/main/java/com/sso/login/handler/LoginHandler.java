package com.sso.login.handler;

import com.sso.login.pojo.User;
import com.sso.login.util.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import sun.rmi.runtime.Log;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login")
public class LoginHandler {

    private static Set<User> dbUsers;

    static {
        dbUsers = new HashSet<>();
        dbUsers.add(new User(1, "zs", 123));
        dbUsers.add(new User(2, "ls", 123));
        dbUsers.add(new User(3, "ww", 123));
    }

    @PostMapping
    public String doLogin(final User user, HttpSession session,
                          HttpServletResponse response){
        String target = (String) session.getAttribute("target");

        Optional<User> first = dbUsers.stream().filter(dbUser -> dbUser.getUsername().equals(user.getUsername()) &&
                dbUser.getPassword().equals(user.getPassword())).findFirst();
        //判断用户是否登陆
        if(first.isPresent()){
            String token = UUID.randomUUID().toString();
            //将taken存入cookie
            Cookie cookie = new Cookie("TOKEN",token);
            //域名
            cookie.setDomain("codeshop.com");
            response.addCookie(cookie);
            //保存登陆用户登陆信息
            LoginCacheUtil.LoginUser.put(token,first.get());
        }else{
            session.setAttribute("msg","用户名或密码输入错误!");
            return "Login";
        }
        //重定向到target页面
        return "redirect:"+target;
    }

    /**
     * 对外开放的方法
     * @param token
     * @return
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token){
        if(!StringUtils.isEmpty(token)){
            User user = LoginCacheUtil.LoginUser.get(token);
            return ResponseEntity.ok(user);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
