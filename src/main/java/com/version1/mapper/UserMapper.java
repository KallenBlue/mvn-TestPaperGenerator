package com.version1.mapper;

import com.version1.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    List<User> selectAllUserInfo();
    @Select("select * from user where account=#{account}")
    User selectUserInfoByAccount(String account);
}
