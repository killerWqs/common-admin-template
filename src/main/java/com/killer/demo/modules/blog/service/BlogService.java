package com.killer.demo.modules.blog.service;

import com.killer.demo.modules.blog.model.Blogs;

import java.util.List;

/**
 * @author killer
 * @date 2019/2/1 -  14:19
 **/
public interface BlogService {
    /**
     * 获取所有的博客文章，并且需要分页
     * @return List<Blogs>
     */
    List<Blogs> blogManage(int page, int pageSize);
}
