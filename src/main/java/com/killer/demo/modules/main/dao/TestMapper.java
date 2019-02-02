package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.Test;
import java.util.List;

public interface TestMapper {
    int deleteByPrimaryKey(String id);

    int insert(Test record);

    Test selectByPrimaryKey(String id);

    List<Test> selectAll();

    int updateByPrimaryKey(Test record);
}