package cn.peakline.controller;

import cn.peakline.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 *
 * @author maxiaoliang
 * @create 2017-04-26 下午2:47
 **/
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonDao personDao;

    @Autowired
    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }
}
