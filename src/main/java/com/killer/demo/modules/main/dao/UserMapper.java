package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}