package cn.peakline.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户资料
 *
 * @author maxiaoliang
 * @create 2017-04-26 下午2:19
 **/
@Entity
@Table(name = "t_person")
@Data
@Accessors(chain = true)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userId;
    private String name;
    private String phone;
    private String address;
    private String description;
    private Date birthday;
    private Date createDate;
    private Date modifiedDate;
}
