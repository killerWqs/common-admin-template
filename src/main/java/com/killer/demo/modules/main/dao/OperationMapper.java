package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.Operation;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface OperationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Operation record);

    Operation selectByPrimaryKey(String id);

    List<Operation> selectAll();

    int updateByPrimaryKey(Operation record);

    List<Operation> selectByMenuId(@Param("menuId") String menuId);
}