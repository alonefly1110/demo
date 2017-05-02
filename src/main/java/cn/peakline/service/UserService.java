package cn.peakline.service;

import cn.peakline.dao.UserDao;
import cn.peakline.dto.UserDTO;
import cn.peakline.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * userService
 *
 * @author maxiaoliang
 * @create 2017-04-24 下午5:16
 **/
@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public User getUserByUserName(String username) {
        return userDao.getUserByUsername(username);
    }

    public void saveUser(UserDTO userInfo) {
        User user = userDao.findOne(userInfo.getUserId());
        if (user == null) {
            user = new User();
            user.setCreateDate(userInfo.getModifiedDate());
        }
        BeanUtils.copyProperties(userInfo, user);
        userDao.save(user);

    }

    public User getUserByUsernameAndPassword(UserDTO userInfo) {
        return userDao.getUserByUsernameAndPassword(userInfo.getUsername(), userInfo.getPassword());
    }

    public User getUserById(Long userId) {
        return userDao.findOne(userId);
    }
}
