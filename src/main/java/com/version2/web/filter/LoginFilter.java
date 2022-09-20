package com.version2.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String[] allowUrl = {"/login.html", "register.html", "/register", "/LoginValidate", "/css/", "/images/", "/mailCode"};

        String url = httpServletRequest.getRequestURL().toString();
        System.out.println(url);
        for (String u :
                allowUrl) {
            if (url.contains(u)) {
                //放行并return
                chain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }

        //判断session中是否有用户
        HttpSession session = httpServletRequest.getSession();

        Object user = session.getAttribute("account");

        if (user != null) {
            System.out.println(user);
            //放行
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            Cookie msg = new Cookie("msg","please_login");
            msg.setMaxAge(60);
            httpServletResponse.addCookie(msg);
            httpServletRequest.getRequestDispatcher("/login.html").forward(httpServletRequest, httpServletResponse);
        }
    }
}
