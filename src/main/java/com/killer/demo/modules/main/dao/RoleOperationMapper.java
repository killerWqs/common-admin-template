package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.RoleOperation;
import feign.Param;

import java.util.List;

public interface RoleOperationMapper {
    int deleteByPrimaryKey(String id);

    int insert(RoleOperation record);

    RoleOperation selectByPrimaryKey(String id);

    List<RoleOperation> selectAll();

    int updateByPrimaryKey(RoleOperation record);

    int deleteByOperaId(String s);

    List<RoleOperation> selectByRoleId(@Param("roleId") String roleId);
}