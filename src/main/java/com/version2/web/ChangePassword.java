package com.version2.web;

import com.version2.pojo.User;
import com.version2.service.UserService;
import com.version2.utils.JsonResultUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
    private final UserService service = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=utf-8");

        String account = request.getParameter("account");
        String password = request.getParameter("password");

        User user = new User(account,password);
        service.updatePassword(user);
        response.getWriter().write(JsonResultUtil.getJson());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
