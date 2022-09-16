package com.version1;

import com.version1.mapper.UserMapper;
import com.version1.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String account = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("account:"+account);
        System.out.println("password:"+password);
        //1.加载mybatis的核心配置文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象，用它来执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.执行sql
//        List<User> userList = sqlSession.selectList("user.selectAllUserInfo");
        //3.1获取userMapper的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectUser(account,password);

        if (user!=null){
            response.sendRedirect(request.getContextPath()+"/choose.html");
        }
        else{
            response.sendRedirect(request.getContextPath()+"/login.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
