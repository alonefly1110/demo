package cn.peakline.service;

import cn.peakline.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * person
 *
 * @author maxiaoliang
 * @create 2017-04-26 下午3:00
 **/
@Service
public class PersonService {
    private final PersonDao personDao;

    @Autowired
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }
}
