package cn.peakline.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户详情
 *
 * @author maxiaoliang
 * @create 2017-04-26 下午2:47
 **/
@Data
public class PersonDTO {
    private int userId;
    private String name;
    private String phone;
    private String address;
    private String sex;
    private Date birthday;
    private String description;
}
