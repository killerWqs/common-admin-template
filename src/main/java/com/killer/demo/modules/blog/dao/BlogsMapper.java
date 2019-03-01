package com.killer.demo.modules.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.killer.demo.modules.blog.model.Blogs;
import java.util.List;

public interface BlogsMapper extends BaseMapper<Blogs> {
    int deleteByPrimaryKey(String id);

    int insert(Blogs record);

    Blogs selectByPrimaryKey(String id);

    List<Blogs> selectAll();

    int updateByPrimaryKey(Blogs record);
}