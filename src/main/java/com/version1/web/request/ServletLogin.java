package com.version1.web.request;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * 1个servlet可以配置多个urlPattern
 * 优先级由高到低
 * 精确匹配
 * 目录匹配 /user/*
 * 扩展名匹配 *.do 不能以/开头
 * 任意匹配 /* 或者 / 不能配置否则无法加载静态资源
 */
@WebServlet(urlPatterns = "/templates/login",loadOnStartup = 1)
public class ServletLogin implements Servlet {
    /**
     * 初始化方法
     * 1.调用时机，默认情况下，Servlet被第一次访问时调用
     *      * loadOnStartup
     * 2.调用次数：1次
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 提供服务
     * 1.调用时机：每一次访问时调用
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("service启动成功");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * 销毁方法
     * 1.调用时机：内存释放或者服务器关闭，Servlet被销毁
     */
    @Override
    public void destroy() {

    }
}
