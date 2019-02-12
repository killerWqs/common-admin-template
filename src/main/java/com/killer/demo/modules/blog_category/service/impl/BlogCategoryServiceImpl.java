package com.killer.demo.modules.blog_category.service.impl;

import com.killer.demo.modules.blog_category.dao.BlogCategoryMapper;
import com.killer.demo.modules.blog_category.model.BlogCategory;
import com.killer.demo.modules.blog_category.service.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@description
 *@author killer
 *@date 2019/02/09 - 11:44
 */
@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {

    private BlogCategoryMapper blogCategoryMapper;

    @Autowired
    public BlogCategoryServiceImpl(BlogCategoryMapper blogCategoryMapper) {
        this.blogCategoryMapper = blogCategoryMapper;
    }

    @Override
    public List<BlogCategory> blogCategoryManage() {
        return blogCategoryMapper.selectAll();
    }

    @Override
    public void addBlogCategory(BlogCategory blogCategory) {
        blogCategoryMapper.insert(blogCategory);
    }

    @Override
    public void deleteBlogCategories(String[] categories) {
        blogCategoryMapper.deleteByPrimaryKeys(categories);
    }
}
