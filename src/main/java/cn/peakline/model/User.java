package cn.peakline.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * user
 *
 * @author maxiaoliang
 * @create 2017-04-24 下午5:13
 **/
@Entity
@Table(name = "t_user")
@Data
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String email;
    private Date createDate;
    private Date modifiedDate;
}
