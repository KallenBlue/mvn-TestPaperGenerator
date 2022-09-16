package com.version1.web.request;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyHttpServlet implements Servlet {
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
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        //1.获取请求方式
        String method = request.getMethod();
        //2.根据不同请求进行不同处理
        if("GET".equals(method)){
            //get处理逻辑
            doGet(servletRequest,servletResponse);
        }
        else if("POST".equals(method)){
            //post处理逻辑
            doPost(servletRequest,servletResponse);
        }
    }

    protected void doPost(ServletRequest servletRequest, ServletResponse servletResponse) {
    }

    protected void doGet(ServletRequest servletRequest, ServletResponse servletResponse) {
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
