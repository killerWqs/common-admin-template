package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.Operation;
import java.util.List;

public interface OperationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Operation record);

    Operation selectByPrimaryKey(String id);

    List<Operation> selectAll();

    int updateByPrimaryKey(Operation record);
}