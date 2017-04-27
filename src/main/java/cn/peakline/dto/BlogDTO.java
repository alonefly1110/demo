package cn.peakline.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 博客相关信息
 *
 * @author maxiaoliang
 * @create 2017-04-27 上午10:36
 **/
@Data
@Accessors(chain = true)
public class BlogDTO {

    private Integer userId;
    private String title;
    private String content;
}
