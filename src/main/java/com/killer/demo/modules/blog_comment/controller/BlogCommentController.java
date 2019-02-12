package com.killer.demo.modules.blog_comment.controller;

import com.killer.demo.modules.blog_comment.model.BlogComments;
import com.killer.demo.modules.blog_comment.service.BlogCommentService;
import com.killer.demo.utils.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author killer
 * @description
 * @date 2019/02/08 - 11:29
 */
@RestController
@RequestMapping("blogcomment")
public class BlogCommentController {

    private BlogCommentService blogCommentService;

    public BlogCommentController(BlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @GetMapping("manage")
    public Response<List<BlogComments>> blogCommentManage(@RequestParam(value = "isChecked", required = false) Integer isChecked, @RequestParam(value = "checkedId", required = false) String checkedId,
                          @RequestParam(value = "startDate", required = false) Date startDate, @RequestParam(value = "endDate", required = false) Date endDate,
                          @RequestParam("page") int page, @RequestParam("limit") int pageSize) {
        List<BlogComments> blogComments = blogCommentService.blogCommentManage(isChecked, checkedId, startDate, endDate, page, pageSize);
        Response<List<BlogComments>> listResponse = new Response<>();
        listResponse.setData(blogComments);
        listResponse.setCount(blogComments.size());
        return listResponse;
    }

    /**
     *  修改评论状态
     *  put 请求需要添加filter转换body中内容到paramters中，来使用requestparam，好像也可以通过 requestbody获取
     * @param checked
     * @param ids
     * @param request
     */
    @PutMapping("check")
    public void blogCommentCheck(@RequestParam("checked") int checked, @RequestParam("ids[]") String[] ids, HttpServletRequest request) {
//    public void blogCommentCheck(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        UserDetails details = (UserDetails)authentication.getDetails();
        // username我存的user.id
        String username = details.getUsername();
        blogCommentService.blogCommentCheck(ids, checked, username);
    }
}
