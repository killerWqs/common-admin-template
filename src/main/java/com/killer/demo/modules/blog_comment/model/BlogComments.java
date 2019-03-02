package com.killer.demo.modules.blog_comment.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

// 这样的实体类有点像jfinal的东西
@Data
@Accessors(chain = true)
@TableName("blog_comments")
public class BlogComments {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("content")
    private String content;

    @TableField("name")
    private String name;

    @TableField("mail_address")
    private String mailAddress;

    @TableField("blog_id")
    private String blogId;

    @TableField("checked")
    private int checked;

    @TableField("checked_id")
    private String checkedId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "intime", fill = FieldFill.INSERT)
    private LocalDateTime intime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "updatetime", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatetime;
}