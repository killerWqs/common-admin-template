package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.Menu;
import com.killer.demo.modules.main.model.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(RoleMenu record);

    RoleMenu selectByPrimaryKey(String roleId);

    List<RoleMenu> selectAll();

    int updateByPrimaryKey(RoleMenu record);

    List<Menu> selectMenusByUserId(@Param("userId") String userId);
}