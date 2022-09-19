package com.version2.web;

import com.version2.utils.JsonResultUtil;
import com.version2.utils.SendMailUtil;

import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/mailCode")
public class mailCode extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应数据
        response.setContentType("text/json;charset=utf-8");
        String mail = request.getParameter("mail");

        //发送邮件，返回发送状态
        String code;
        try {
            code = SendMailUtil.sendMail(mail,4);
            if (!"".equals(code)){
                //发送成功
                //将code存入session
                HttpSession session = request.getSession();
                session.setAttribute("code", code);
                response.getWriter().write(JsonResultUtil.getJson());
            }
            else {
                //发送失败
                response.getWriter().write(JsonResultUtil.getJsonFail(201,"code_is_empty"));
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().write(JsonResultUtil.getJsonFail(202,"error"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
