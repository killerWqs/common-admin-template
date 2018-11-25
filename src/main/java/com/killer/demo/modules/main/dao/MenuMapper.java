package com.killer.demo.modules.main.dao;

import com.killer.demo.modules.main.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(Menu record);

    Menu selectByPrimaryKey(String id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    @Select("select `id`, `name`, `icon`, `url`, `level`, `order`, `fid` from menu where `user_id` = #{user_id}")
    List<Menu> selectAllByUserId(@Param("user_id") String userId);
}