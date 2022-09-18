package com.version2.mapper;

import com.version2.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where account=#{account} and password=#{password}")
    User selectUser(@Param("account") String account,@Param("password") String password);

    @Select("select * from user where account=#{account}")
    User selectUserByAccount(String account);

    @Insert("insert into user values(#{account},#{password},'小学')")
    void add(User user);

}
