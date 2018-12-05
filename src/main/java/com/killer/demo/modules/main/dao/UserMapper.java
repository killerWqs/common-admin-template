package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectUserAll(@Param("username") String username, @Param("roleId") String roleId);

    int updateByPrimaryKey(User record);

    @Select("select id from user where username=#{username}")
    String checkUserNameUnique(@Param("username") User username);
}