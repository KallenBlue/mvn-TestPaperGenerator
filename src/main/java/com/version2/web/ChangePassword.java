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

        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account");
        User user = service.selectUserByAccount(account);
        String realPassword = user.getPassword();
        String originPassword = request.getParameter("origin_password");
        if (!realPassword.equals(originPassword)){
            //密码错误
            response.getWriter().write(JsonResultUtil.getJson(201,"wrong_password"));
            return;
        }
        String newPassword = request.getParameter("new_password");
        if (newPassword.equals(originPassword)){
            //与原来的密码相同
            response.getWriter().write(JsonResultUtil.getJsonFail(202,"duplicate_password"));
            return;
        }
        user.setPassword(newPassword);
        service.updatePassword(user);
        response.getWriter().write(JsonResultUtil.getJson());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
