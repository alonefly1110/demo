package cn.peakline.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;

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

    public static void createCookie(HttpServletResponse response, String cookieValue, int maxAge, String path) {
        Cookie UserCookie = new Cookie("fashion-token", cookieValue);
        UserCookie.setMaxAge(maxAge);
        UserCookie.setPath(path);
        response.addCookie(UserCookie);
    }
}
