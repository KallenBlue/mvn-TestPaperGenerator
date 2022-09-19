package com.version2.service;

import com.version2.mapper.UserMapper;
import com.version2.pojo.User;
import com.version2.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public User login(String account, String password){
        //获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //获取UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //调用方法
        User user = mapper.selectUser(account,password);

        sqlSession.close();

        return user;
    }

    public boolean register(User user){
        //获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //获取mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //检查是否已经存在
        User existUser = userMapper.selectUserByAccount(user.getAccount());

        if (existUser==null){
            //用户不存在，注册用户
            userMapper.add(user);
            sqlSession.commit();
        }
        sqlSession.close();
        return existUser==null;
    }

    public void updatePassword(User user){
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updatePassword(user);
        sqlSession.commit();
        sqlSession.close();
    }
}
