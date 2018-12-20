package com.sucaiji.pagecache.controller;


import com.sucaiji.pagecache.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyController {

    @Autowired
    private MyService myService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {
        String html = myService.getHtmlFromCache(request.getRequestURI());
        if (html != null) {
            System.out.println("缓存不为空");
            return html;
        }

        //手动渲染index页面
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("index", ctx);

        myService.saveCache(request.getRequestURI(), html);
        System.out.println("缓存为空，保存缓存");
        return html;
    }
}
