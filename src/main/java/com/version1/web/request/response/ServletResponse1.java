package com.version1.web.request.response;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/ServletResponse1")
public class ServletResponse1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("resp1...");
        response.sendRedirect(request.getContextPath()+"/templates/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        //重定向
//        //1.设置状态码
//        response.setStatus(302);
//        //2.设置响应头
//        response.setHeader("Location","/mvn_TestPaperGenerator_war/templates/choose.html");
    }
}
