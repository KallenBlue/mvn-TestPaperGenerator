package com.version2.web;

import com.version2.pojo.User;
import com.version2.service.UserService;
import com.version2.utils.JsonResultUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/register")
public class register extends HttpServlet {
    private final UserService service = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //设置响应数据
        response.setContentType("text/json;charset=utf-8");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String validate = request.getParameter("validate");
        System.out.println("validate:"+validate);
        //程序生成的验证码，从Session获取
        HttpSession session = request.getSession();
        String checkCode = (String) session.getAttribute("code");
//        checkCode = "7665";
        System.out.println("checkCode:"+checkCode);
        if (!checkCode.equals(validate)){
            //返回验证码错误的消息
            //响应返回数据
            response.getWriter().write(JsonResultUtil.getJson("wrong_code",new Object()));
//            System.out.println("wrong_code:");
            return;
        }
        User user = new User(account,password);


        boolean flag = service.register(user);
        if (flag){
            //满足注册条件，则跳转到登录页面并附带刚添加的账号信息
            Cookie cookie = new Cookie("account",account);
            //设置cookie存活时间
            cookie.setMaxAge(60*60);
            response.addCookie(cookie);
            //响应返回数据
            //将用户账号添加到json中去
            HashMap<String ,String> accountMap = new HashMap<>();
            accountMap.put("account",account);
            response.getWriter().write(JsonResultUtil.getJson(accountMap));
            //重定向到choose页面
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath+"/login.html");

        }
        else {
            //不满足注册条件，表示用户名已被注册
            response.getWriter().write(JsonResultUtil.getJsonFail());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
