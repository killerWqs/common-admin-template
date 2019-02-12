package com.killer.demo.modules.blog_category.dao;

import com.killer.demo.modules.blog_category.model.BlogCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(BlogCategory record);

    BlogCategory selectByPrimaryKey(String id);

    List<BlogCategory> selectAll();

    int updateByPrimaryKey(BlogCategory record);

    void deleteByPrimaryKeys(@Param("categories") String[] categories);
}