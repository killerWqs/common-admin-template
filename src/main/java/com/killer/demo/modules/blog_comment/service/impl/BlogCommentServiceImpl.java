package com.killer.demo.modules.blog_comment.service.impl;/**
 * @author killer
 * @date 2019/2/8 -  11:43
 **/

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.killer.demo.modules.blog_comment.dao.BlogCommentsMapper;
import com.killer.demo.modules.blog_comment.model.BlogComments;
import com.killer.demo.modules.blog_comment.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *@description
 *@author killer
 *@date 2019/02/08 - 11:43
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements BlogCommentService {

    @Autowired
    public BlogCommentServiceImpl(BlogCommentsMapper blogCommentsMapper) {
        this.baseMapper = blogCommentsMapper;
    }

    @Override
    public List<BlogComments> blogCommentManage(Integer isChecked, String checkedId, Date startDate, Date endDate, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<BlogComments> blogComments = baseMapper.selectAllByCriteria(isChecked, checkedId, startDate, endDate);
        return blogComments;
    }

    @Override
    public void blogCommentCheck(String[] commentId, int checked, String checkedId) {
        baseMapper.batchUpdateChecked(commentId, checked, checkedId);
    }

    @Override
    public void insertBlogComment(BlogComments blogComment) {
        // mybatis plus 生成id的算法
    }
}
