package com.killer.demo.modules.blog.controller;/**
 * @author killer
 * @date 2019/2/1 -  14:09
 **/

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.killer.demo.modules.blog.exception.UploadImageFailedException;
import com.killer.demo.modules.blog.model.Blogs;
import com.killer.demo.modules.blog.model.OSSAccount;
import com.killer.demo.modules.blog.service.BlogService;
import com.killer.demo.utils.RandomUtils;
import com.killer.demo.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author killer
 * @description
 * @date 2019/02/01 - 14:09
 */
@RestController
@RequestMapping("blog")
public class BlogController {
    private OSSAccount ossAccount;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService, OSSAccount ossAccount) {
        this.blogService = blogService;
        this.ossAccount = ossAccount;
    }

    /**
     *  返回所有博客文章
     *  pojo里没有相应private属性的getter时是不会返回的
     * @param page 当前页
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("manage")
    public Response blogManage(@RequestParam("page") int page, @RequestParam("limit") int pageSize) {
        List<Blogs> blogs = blogService.blogManage(page, pageSize);
        Response<List<Blogs>> listResponse = new Response<>();
        listResponse.setData(blogs);
        listResponse.setCount(blogs.size());
        return listResponse;
    }


    /**
     * 添加博客
     * @param blogs 博客文章
     */
    @PostMapping
    public void addBlog(Blogs blogs) {
        blogService.addBlog(blogs);
    }


    @PostMapping("images")
    public Response blogImages(@RequestParam("file") MultipartFile multipartFile) throws IOException, UploadImageFailedException {
        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String filename = originalFilename.substring(0, index - 1);
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String finalName = "blogimages/" + filename + "-" + RandomUtils.uuid() + originalFilename.substring(index);


        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, ossAccount.getAccessKeyId(), ossAccount.getAccessKeySecret());

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        try {
            ossClient.putObject(ossAccount.getBucketName(), finalName, new ByteArrayInputStream(multipartFile.getBytes()));
        } catch (IOException e) {
            throw e;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        Response<String> stringResponse = new Response<>();
        stringResponse.setData(finalName);
        return stringResponse;
    }

    @GetMapping("images")
    public void blogImages(@RequestParam("filePath") String filePath, HttpServletResponse response) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。

// 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, ossAccount.getAccessKeyId(), ossAccount.getAccessKeySecret());
// 这里从oss获取图片 返回前台
// 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(ossAccount.getBucketName(), filePath);
// 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        ServletOutputStream outputStream = response.getOutputStream();
        if (content != null) {
            // 图片处理方式 有问题 传过来的就少了
            BufferedInputStream bufferedInputStream = new BufferedInputStream(content);
            byte[] buffer = new byte[1024];
            while (bufferedInputStream.available() != 0) {
                if (bufferedInputStream.available() > 1024) {
                    bufferedInputStream.read(buffer, 0, 1024);
                } else {
                    bufferedInputStream.read(buffer, 0, bufferedInputStream.available());
                }

                outputStream.write(buffer);
                buffer = new byte[1024];
//                这应该是文本文件的处理方式
//                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                while (true) {
//                    String line = reader.readLine();
//                    if (line == null) {
//                        break;
//                    }
//                    System.out.println("\n" + line);
//                }

            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            content.close();
        }

// 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void main(String[] args) {
//        System.out.println(Arrays.toString("123.jpg".split(".+/.[^/.]+$")));
        String filepath = "C:\\Users\\12494\\Pictures\\151-1G204103113.jpg";
        String copy_name = "C:\\Users\\12494\\Pictures\\123.jpg";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(copy_name);
            FileInputStream fileInputStream = new FileInputStream(filepath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[1024];
            int startIndex = 0;
            // bufferedInputStream.available 获取到的是字节码
            while (bufferedInputStream.available() != 0) {
                System.out.println(bufferedInputStream.available());
                if (bufferedInputStream.available() > 1024) {
                    bufferedInputStream.read(buffer, 0, 1024);
                } else {
                    bufferedInputStream.read(buffer, 0, startIndex + bufferedInputStream.available());
                }

                fileOutputStream.write(buffer);
                buffer = new byte[1024];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
