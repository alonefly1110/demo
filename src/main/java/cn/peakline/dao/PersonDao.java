package cn.peakline.dao;

import cn.peakline.model.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * person
 *
 * @author maxiaoliang
 * @create 2017-04-26 下午3:15
 **/
public interface PersonDao extends CrudRepository<Person,Long> {
}
