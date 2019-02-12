package com.killer.demo.modules.blog_category.controller;

import com.killer.demo.modules.blog_category.model.BlogCategory;
import com.killer.demo.modules.blog_category.service.BlogCategoryService;
import com.killer.demo.utils.DateTimeUtils;
import com.killer.demo.utils.RandomUtils;
import com.killer.demo.utils.Response;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *@description
 *@author killer
 *@date 2019/02/09 - 11:40
 */
@RestController
@RequestMapping("blogcategory")
public class BlogCategoryController {

    private BlogCategoryService blogCategoryService;

    @Autowired
    public BlogCategoryController(BlogCategoryService blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    @GetMapping("manage")
    public Response<List<BlogCategory>> blogCategoryManage() {
        List<BlogCategory> blogCategories = blogCategoryService.blogCategoryManage();
        Response<List<BlogCategory>> listResponse = new Response<>();
        listResponse.setData(blogCategories);
        listResponse.setCount(blogCategories.size());
        return listResponse;
    }

    @PostMapping("manage")
    public void blogCategoryManage(BlogCategory blogCategory, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        UserDetails details = (UserDetails)authentication.getDetails();
        // username我存的user.id
        String username = details.getUsername();

        blogCategory.setId(RandomUtils.uuid());
        blogCategory.setCreator(username);
        blogCategory.setIntime(DateTimeUtils.now());
        blogCategory.setUpdatetime(DateTimeUtils.now());
        blogCategoryService.addBlogCategory(blogCategory);
    }

    @DeleteMapping("manage")
    public void blogCategoryManage(@RequestParam("categories[]") String[] categories) {
        blogCategoryService.deleteBlogCategories(categories);
    }
}
