package com.version1.web.request;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/ServletTest1")
public class ServletTest1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.解决乱码：POSt，
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        System.out.println(username);
        //2.解决GET,tomcat7以上已经解决了get问题了
//        username = new String(username.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
//        System.out.println(username);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
