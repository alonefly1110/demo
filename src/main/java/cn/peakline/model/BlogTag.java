package cn.peakline.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 标签
 *
 * @author maxiaoliang
 * @create 2017-05-02 下午4:35
 **/
@Entity
@Table(name = "t_blog_tag")
@Data
@Accessors(chain = true)
public class BlogTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String tagName;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer tagType;
    @NotNull
    private Date createDate;


}
