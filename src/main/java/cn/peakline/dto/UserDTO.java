package cn.peakline.dto;

import cn.peakline.utils.StringUtil;

import java.util.Date;

/**
 * userInfo
 *
 * @author maxiaoliang
 * @create 2017-04-24 下午4:48
 **/
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String code;
    private Date modifiedDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = StringUtil.getMd5(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Date getModifiedDate() {
        return new Date();
    }

}
