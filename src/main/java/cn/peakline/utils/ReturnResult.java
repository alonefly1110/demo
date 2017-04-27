package cn.peakline.utils;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回结果
 *
 * @author maxiaoliang
 * @create 2017-04-24 下午5:33
 **/
@Data
@Accessors(chain = true)
public class ReturnResult {
    private int status;
    private String msg;
    private Object object;
}
