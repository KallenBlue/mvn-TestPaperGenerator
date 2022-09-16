package com.version1.web.request.response;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ServletResponse4")
public class ServletResponse4 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.读取文件
        FileInputStream fileInputStream = new FileInputStream("D:\\图片\\图库\\.1ke593.jpg");
        //2. 获取response字节输出流
        ServletOutputStream servletOutputStream = response.getOutputStream();
        //3.完成流的copy、
        IOUtils.copy(fileInputStream,servletOutputStream);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
