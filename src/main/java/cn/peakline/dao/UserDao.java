package cn.peakline.dao;

import cn.peakline.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * userDao
 *
 * @author maxiaoliang
 * @create 2017-04-24 下午5:15
 **/
public interface UserDao extends CrudRepository<User,Long>{
    User getUserByUsername(String username);
    User getUserByUsernameAndPassword(String username, String password);
}
