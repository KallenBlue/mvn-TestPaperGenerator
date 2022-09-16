package com.version1.web.request;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/templates/test")
public class ServletTest extends MyHttpServlet{
    @Override
    protected void doPost(ServletRequest servletRequest, ServletResponse servletResponse) {
        //获取post 请求体：请求参数

        //1.获取字符输入流
        try (BufferedReader br = servletRequest.getReader()) {
            //2.读取数据
            String line = br.readLine();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(ServletRequest servletRequest, ServletResponse servletResponse) {
        //获取请求行的数据
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String method = request.getMethod();
        System.out.println(method);
        String contextPath = request.getContextPath();
        System.out.println(contextPath);
        StringBuffer url = request.getRequestURL();
        System.out.println(url);
        String uri = request.getRequestURI();
        System.out.println(uri);
        String queryString = request.getQueryString();
        System.out.println(queryString);
        //获取请求头
        String agent = request.getHeader("user-agent");
        System.out.println(agent);
    }
}
