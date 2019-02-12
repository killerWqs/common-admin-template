package com.killer.demo.modules.blog_category.service;/**
 * @author killer
 * @date 2019/2/9 -  11:43
 **/

import com.killer.demo.modules.blog_category.model.BlogCategory;

import java.util.List;

/**
 *@description
 *@author killer
 *@date 2019/02/09 - 11:43
 */
public interface BlogCategoryService {
    List<BlogCategory> blogCategoryManage();

    void addBlogCategory(BlogCategory blogCategory);

    void deleteBlogCategories(String[] categories);
}
