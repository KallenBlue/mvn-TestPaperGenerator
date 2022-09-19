package com.version2.web;

import com.version2.utils.JsonResultUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/answerValidate")
public class answerValidate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //设置响应数据
        response.setContentType("text/json;charset=utf-8");

        String userAnswer = request.getParameter("answer");
        String questionID = request.getParameter("question_id");
        System.out.println("userAnswer:" + userAnswer);
        System.out.println("questionID:" + questionID);
        HttpSession session = request.getSession();
        String rightAnswer = (String) session.getAttribute("answer" + questionID);
        System.out.println("rightAnswer:" + rightAnswer);
        if (userAnswer.equals(rightAnswer)) {
            response.getWriter().write(JsonResultUtil.getJson("true"));
        } else {
            response.getWriter().write(JsonResultUtil.getJsonFail(201,"false"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
