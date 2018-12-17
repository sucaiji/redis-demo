package com.sucaiji.cookiecache.controller;

import com.sucaiji.cookiecache.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MyController {

    @Autowired
    private MyService myService;

    /**
     * 登录接口，登录成功后，会生成一个token存储在redis里面
     * @param account
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletResponse response,
                        @RequestParam("account") String account,
                        @RequestParam("password") String password) {
        String token = myService.checkLogin(account, password);
        if (token == null) {
            return "fail";
        }
        response.addCookie(new Cookie("token", token));
        return "success";
    }

    /**
     * 正文页面
     * @param request
     * @return
     */
    @RequestMapping("/page")
    public String page(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "未登录，请登录";
        }
        String token = null;
        for (Cookie cookie: cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        if (token == null) {
            return "未登录，请登录";
        }
        String account = myService.checkToken(token);
        if (account == null) {
            return "请重新登陆";
        }
        return "正文";
    }

}
