package cn.peakline.dao;

import cn.peakline.model.Blog;
import org.springframework.data.repository.CrudRepository;

/**
 * blogDao
 *
 * @author maxiaoliang
 * @create 2017-05-02 下午4:22
 **/
public interface BlogDao extends CrudRepository<Blog, Integer> {
}
