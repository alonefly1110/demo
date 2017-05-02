package cn.peakline.service;

import cn.peakline.dao.BlogDao;
import cn.peakline.dto.BlogDTO;
import cn.peakline.model.Blog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * blogService
 *
 * @author maxiaoliang
 * @create 2017-05-02 下午4:21
 **/
@Service
public class BlogService {
    private final BlogDao blogDao;

    @Autowired
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public BlogDTO getBlogById(Integer blogId) {
        BlogDTO blogDTO = new BlogDTO();
        Blog blog = blogDao.findOne(blogId);
        BeanUtils.copyProperties(blog, blogDTO);
        return blogDTO;
    }
}
