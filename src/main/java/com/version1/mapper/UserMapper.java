package com.version1.mapper;

import com.version1.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    List<User> selectAllUserInfo();
    @Select("select * from user where account=#{account}")
    User selectUserInfoByAccount(String account);

    @Select("select * from user where account=#{account} and password=#{password}")
    User selectUser(@Param("account")String account,@Param("password") String password);
}
