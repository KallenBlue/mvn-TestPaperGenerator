package com.version1;

import com.version1.mapper.QuestionMapper;
import com.version1.mapper.UserMapper;
import com.version1.pojo.Question;
import com.version1.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class test {
    @Test
    public void testUserSelectByAccount() throws IOException {
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
        User user = mapper.selectUserInfoByAccount("张三1");
        System.out.println(user);
        //4.释放资源
        sqlSession.close();
    }
    @Test
    public void testQuestionSelectAll() throws IOException {
        //1.加载mybatis的核心配置文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象，用它来执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.执行sql
//        List<User> userList = sqlSession.selectList("user.selectAllUserInfo");
        //3.1获取userMapper的代理对象
        QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
        List<Question> userList = questionMapper.selectAllQuestions();
        System.out.println(userList);
        //4.释放资源
        sqlSession.close();
    }
    @Test
    public void testQuestionSelectQuestionByID() throws IOException {
        int id = 971;
        //1.加载mybatis的核心配置文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象，用它来执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.执行sql
//        List<User> userList = sqlSession.selectList("user.selectAllUserInfo");
        //3.1获取userMapper的代理对象
        QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
        Question question = questionMapper.selectQuestionByID(id);
        System.out.println(question);
        //4.释放资源
        sqlSession.close();
    }
}
