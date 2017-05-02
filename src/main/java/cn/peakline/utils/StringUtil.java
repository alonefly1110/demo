package cn.peakline.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * stringUtil
 *
 * @author maxiaoliang
 * @create 2017-04-24 下午5:20
 **/
public class StringUtil {

    private static MessageDigest md5 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 用于获取一个String的md5值
     */
    public static String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString().toUpperCase();
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        for (Cookie cookie : request.getCookies()) {
            if (cookieName.equals(cookie.getName())) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }

    }

    public static void createCookie(HttpServletResponse response, String cookieValue, int maxAge) {
        Cookie UserCookie = new Cookie("fashion-token", cookieValue);
        UserCookie.setMaxAge(maxAge);
        UserCookie.setPath("/");
        response.addCookie(UserCookie);
    }

    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";


    public static void main(String[] args) throws QiniuException {
        String dataString = "<img data-s=\"300,640\" data-type=\"png\" data-src=\"http://mmbiz.qpic.cn/mmbiz_png/zkA8jrXwg5YGz1tYEZfsZUyJ7qn7sY2aw7RN0QG5iarqA7vt2Eiakggticp578L3bowgBlyniaMcD3XfEXpmicEULmQ/0?wx_fmt=png\" data-ratio=\"0.5413669064748201\" data-w=\"\" style=\"width: auto ! important; height: auto ! important; visibility: visible ! important;\" class=\" \" src=\"http://mmbiz.qpic.cn/mmbiz_png/zkA8jrXwg5YGz1tYEZfsZUyJ7qn7sY2aw7RN0QG5iarqA7vt2Eiakggticp578L3bowgBlyniaMcD3XfEXpmicEULmQ/640?wx_fmt=png&amp;wxfrom=5&amp;wx_lazy=1\" data-fail=\"0\">";
        List<String> list = getImageUrl(dataString);
        List<String> srcList = getImageSrc(list);
        Auth auth = Auth.create(Constants.QINIU_ACCESS_KEY, Constants.QINIU_SECRET_KEY);
        Configuration configuration = new Configuration();
        BucketManager bm = new BucketManager(auth, configuration);
        for (String s : srcList) {
            FetchRet putret = bm.fetch(s, Constants.BUCKET_NAME, String.valueOf(System.currentTimeMillis()));
            String url = Constants.QINIU_DOMAIN + "/" + putret.key;
            dataString = dataString.replace(s, url);
        }


    }

    /**
     * 获取ImageUrl地址
     */
    private static List<String> getImageUrl(String HTML) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
        List<String> listImgUrl = new ArrayList<>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
    }

    /***
     * 获取ImageSrc地址
     *
     */
    private static List<String> getImageSrc(List<String> listImageUrl) {
        List<String> listImgSrc = new ArrayList<>();
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return listImgSrc;
    }
}
