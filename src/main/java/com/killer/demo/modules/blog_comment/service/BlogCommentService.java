package com.killer.demo.modules.blog_comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.killer.demo.modules.blog_comment.model.BlogComments;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.List;

/**
 *@description
 *@author killer
 *@date 2019/02/08 - 11:37
 */
public interface BlogCommentService {

    /**
     *  根据条件获取评论列表
     * @param isChecked 是否审核通过
     * @param checkedId 审核人id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return List<BlogComments>
     */
    public List<BlogComments> blogCommentManage(Integer isChecked, String checkedId, Date startDate, Date endDate, int page, int pageSize);

    void blogCommentCheck(String[] commentId, int checked, String checkedId);

    void insertBlogComment(BlogComments blogComment);
}
