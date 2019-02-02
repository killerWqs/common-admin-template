package com.killer.demo.modules.blog.controller;/**
 * @author killer
 * @date 2019/2/1 -  14:09
 **/

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.killer.demo.modules.blog.exception.UploadImageFailedException;
import com.killer.demo.modules.blog.model.Blogs;
import com.killer.demo.modules.blog.service.BlogService;
import com.killer.demo.utils.RandomUtils;
import com.killer.demo.utils.Response;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *@description
 *@author killer
 *@date 2019/02/01 - 14:09
 */
@RestController
@RequestMapping("blog")
public class BlogController {

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("manage")
    public Response blogManage(@RequestParam("page") int page, @RequestParam("limit") int pageSize) {
        List<Blogs> blogs = blogService.blogManage(page, pageSize);
        Response<List<Blogs>> listResponse = new Response<>();
        listResponse.setData(blogs);
        return  listResponse;
    }

    @PostMapping("images")
    public Response blogImages(@RequestParam("file") MultipartFile multipartFile) throws IOException, UploadImageFailedException {
        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String filename = originalFilename.substring(0, index - 1);
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIW2VVYMaGaykl";
        String accessKeySecret = "ZLxoXG7EG1ejims2045Ef4aeSP4j8M";
        String bucketName = "avatarimageserver";
        String finalName = filename + "-" + RandomUtils.uuid() + originalFilename.substring(index);

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        String content = "Hello OSS";
        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, finalName, new ByteArrayInputStream(multipartFile.getBytes()));
            ResponseMessage response = putObjectResult.getResponse();
            int statusCode = response.getStatusCode();
            if(statusCode == 200) {
                // 返回的string为文件名
                Response<String> stringResponse = new Response<>();
                stringResponse.setData(finalName);
                return stringResponse;
            } else {
                throw new UploadImageFailedException("上传图片失败");
            }
        } catch (IOException | UploadImageFailedException e) {
            throw e;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString("123.jpg".split(".+/.[^/.]+$")));
    }
}
