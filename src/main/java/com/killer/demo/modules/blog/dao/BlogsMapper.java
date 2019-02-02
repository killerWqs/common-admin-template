package com.killer.demo.modules.blog.dao;

import com.killer.demo.modules.blog.model.Blogs;
import java.util.List;

public interface BlogsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Blogs record);

    Blogs selectByPrimaryKey(String id);

    List<Blogs> selectAll();

    int updateByPrimaryKey(Blogs record);
}