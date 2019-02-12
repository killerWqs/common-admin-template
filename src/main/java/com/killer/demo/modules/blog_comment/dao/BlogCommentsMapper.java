package com.killer.demo.modules.blog_comment.dao;

import com.killer.demo.modules.blog_comment.model.BlogComments;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BlogCommentsMapper {
    int deleteByPrimaryKey(String id);

    int insert(BlogComments record);

    BlogComments selectByPrimaryKey(String id);

    List<BlogComments> selectAll();

    int updateByPrimaryKey(BlogComments record);

    List<BlogComments> selectAllByCriteria(@Param("isChecked") Integer isChecked, @Param("checkedId") String checkedId,
                                           @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    void batchUpdateChecked(@Param("commentIds") String[] commentId,@Param("checked") int checked, @Param("checkedId") String checkedId);
}