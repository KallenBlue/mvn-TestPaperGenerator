package com.version2.web;

import com.version2.utils.GenerateQuestionsUtil;
import com.version2.utils.JsonResultUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@WebServlet("/question")
public class question extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //设置响应数据
        response.setContentType("text/json;charset=utf-8");
        try {
            int num = Integer.parseInt(request.getParameter("question"));
            String type = request.getParameter("school");
            System.out.println("num:" + num + " type:" + type);
            String[] questions = GenerateQuestionsUtil.generateQuestions(num, type);
            System.out.println("questions" + Arrays.toString(questions));
            HashMap<String, Object> data = new HashMap<>();
            if (questions != null) {
                Object[] questionsInfo = new Object[questions.length];
                data.put("len", questions.length);
                for (int i = 0; i < questions.length; i++) {
                    HashMap<String, Object> questionsData = new HashMap<>();
                    questionsData.put("id", i);
                    questionsData.put("question", questions[i]);
                    String[] options = GenerateQuestionsUtil.getOptions(questions[i]);
                    HttpSession session = request.getSession();
                    session.setAttribute("answer" + i, options[0]);
                    GenerateQuestionsUtil.shuffleOptions(options);
                    questionsData.put("options", options);
                    questionsInfo[i] = questionsData;
                }
                data.put("questions", questionsInfo);
                response.getWriter().write(JsonResultUtil.getJson(data));
            } else {
                response.getWriter().write(JsonResultUtil.getJsonFail(201,"questions_is_null"));
            }
        } catch (Exception NumberFormatException) {
            //参数错误返回
            //响应返回数据
            response.getWriter().write(JsonResultUtil.getJsonFail(202,"wrong_param"));
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
