package cn.peakline.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * 博客
 *
 * @author maxiaoliang
 * @create 2017-04-26 下午4:45
 **/
@Entity
@Table(name = "t_blog")
@Data
@Accessors(chain = true)
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    @Column(name = "is_delete")
    private Short Delete;
    private Date createDate;
    private Date modifiedDate;

}
