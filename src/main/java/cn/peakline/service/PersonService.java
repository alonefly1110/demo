package cn.peakline.service;

import cn.peakline.dao.PersonDao;
import cn.peakline.dto.PersonDTO;
import cn.peakline.model.Person;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public void savePersonInfo(PersonDTO personDTO) {
        Person person = personDao.findByUserId(personDTO.getUserId());
        if (person == null) {
            person = new Person();
            person.setCreateDate(new Date());
        }
        BeanUtils.copyProperties(personDTO, person);
        personDao.save(person);

    }
}
