package com.killer.demo.modules.blog.service.impl;/**
 * @author killer
 * @date 2019/2/1 -  14:23
 **/

import com.github.pagehelper.PageHelper;
import com.killer.demo.modules.blog.dao.BlogsMapper;
import com.killer.demo.modules.blog.model.Blogs;
import com.killer.demo.modules.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@description
 *@author killer
 *@date 2019/02/01 - 14:23
 */
@Service
public class BlogServiceImpl implements BlogService {

    private BlogsMapper blogsMapper;

    @Autowired
    public BlogServiceImpl(BlogsMapper blogsMapper) {
        this.blogsMapper = blogsMapper;
    }

    @Override
    public List<Blogs> blogManage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Blogs> blogs = blogsMapper.selectAll();
        return blogs;
    }
}
