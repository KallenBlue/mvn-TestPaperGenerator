package com.version2.web;

import com.version2.pojo.User;
import com.version2.service.UserService;
import com.version2.utils.JsonResultUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/LoginValidate")
public class LoginValidate extends HttpServlet {
    private final UserService service = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //设置响应数据
        response.setContentType("text/json;charset=utf-8");

        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String verificationCode = request.getParameter("verification_code");
        System.out.println("account:"+account);
        System.out.println("password:"+password);
        System.out.println("verificationCode:"+verificationCode);

        User user = service.login(account,password);

        if (user!=null){
            //登录成功,将登录后的信息存储到cookie和session中
            HttpSession session = request.getSession();
            session.setAttribute("account",account);
            Cookie cookie = new Cookie("account", URLEncoder.encode(account,"utf-8"));
            cookie.setMaxAge(60*60);
            response.addCookie(cookie);
            //删除未登录提示信息的cookie
            Cookie[] loginCookie = request.getCookies();
            for (Cookie c :
                    loginCookie) {
                String msg = c.getName();
                if ("msg".equals(msg)){
                    c.setMaxAge(0);
                    response.addCookie(c);
                    break;
                }
            }
            //响应返回数据
            response.getWriter().write(JsonResultUtil.getJson());
            //重定向到choose页面  
//            String contextPath = request.getContextPath();
//            response.sendRedirect(contextPath+"/choose.html");
        }
        else{
            //登录失败
            response.getWriter().write(JsonResultUtil.getJsonFail(201,"fail"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
