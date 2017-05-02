package cn.peakline.controller;

import cn.peakline.dto.BlogDTO;
import cn.peakline.service.BlogService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客
 *
 * @author maxiaoliang
 * @create 2017-05-02 下午4:20
 **/
@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final Gson gson;

    @Autowired
    public BlogController(BlogService blogService, Gson gson) {
        this.blogService = blogService;
        this.gson = gson;
    }

    @RequestMapping("/saveBlog")
    public String saveBlog(BlogDTO blogDTO) {

        return "";
    }

    @RequestMapping("/getBlogDetail")
    public String getBlogDetail(Integer blogId, String callback) {
        BlogDTO blogDTO = blogService.getBlogById(blogId);
        return callback + "(" + gson.toJson(blogDTO) + ")";
    }

    @RequestMapping("/queryAllBlogs")
    public String queryAllBlogs() {

        return "";
    }
}
