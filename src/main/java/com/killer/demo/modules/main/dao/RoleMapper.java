package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.Role;
import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    Role selectByPrimaryKey(String id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> getRolesAll(String rolename);
}