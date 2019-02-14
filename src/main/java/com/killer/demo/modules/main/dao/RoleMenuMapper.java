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

    /**
     * 用来查找当前用户的权限
     * @param roleId
     * @return
     */
    List<Menu> selectMenusByUserId(@Param("roleId") String roleId);

    /**
     * 用来处理是否授权
     * @param roleId
     * @return
     */
    List<RoleMenu> selectMenusByRoleId(@Param("roleId") String roleId);

    int deleteByMenuId(@Param("menuId") String menuId);
}