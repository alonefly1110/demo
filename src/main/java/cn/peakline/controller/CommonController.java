package cn.peakline.controller;

import cn.peakline.dto.UserDTO;
import cn.peakline.model.User;
import cn.peakline.service.RedisService;
import cn.peakline.service.UserService;
import cn.peakline.utils.*;
import com.google.gson.Gson;
import com.qiniu.util.Auth;
import com.yunpian.sdk.YunpianException;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SmsSingleSend;
import com.yunpian.sdk.service.SmsOperator;
import com.yunpian.sdk.service.YunpianRestClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static cn.peakline.utils.VerifyCodeUtils.generateVerifyCode;
import static cn.peakline.utils.VerifyCodeUtils.outputImage;


/**
 * Demo
 *
 * @author maxiaoliang
 * @create 2017-04-21 下午2:48
 **/
@RestController
@RequestMapping("/common")
public class CommonController {
    private Gson gson = new Gson();
    private Logger logger = Logger.getLogger(CommonController.class);
    private final UserService userService;
    private final RedisService redisService;

    @Autowired
    public CommonController(UserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }

    @RequestMapping("/register")
    public String register(HttpServletRequest request, UserDTO userInfo, String callback) {
        ReturnResult returnResult = new ReturnResult();
        User user = userService.getUserByUserName(userInfo.getUsername());
        String code = (String) request.getSession().getAttribute("code");
        logger.warn(code + "  " + userInfo.getPassword());
        if (code == null || !code.equalsIgnoreCase(userInfo.getCode())) {
            returnResult.setStatus(Constants.FAIL_STATUS).setMsg("验证码错误");
            return callback + "(" + gson.toJson(returnResult) + ")";
        }
        if (user != null) {
            returnResult.setStatus(Constants.FAIL_STATUS).setMsg("该用户已存在");
            return callback + "(" + gson.toJson(returnResult) + ")";
        }
        userService.saveUser(userInfo);
        returnResult.setStatus(Constants.SUCCESS_STATUS).setMsg("注册成功！");
        return callback + "(" + gson.toJson(returnResult) + ")";
    }

    @RequestMapping("/login")
    public String login(String callback, UserDTO userInfo, HttpServletResponse response, HttpServletRequest request) {
        User user = userService.getUserByUsernameAndPassword(userInfo);
        ReturnResult returnResult = new ReturnResult();
        String code = (String) request.getSession().getAttribute("code");
        if (code == null || !code.equalsIgnoreCase(userInfo.getCode())) {
            returnResult.setStatus(Constants.FAIL_STATUS).setMsg("验证码错误");
            return callback + "(" + gson.toJson(returnResult) + ")";
        }
        if (user != null) {
            UUID uuid = UUID.randomUUID();
            Map<String, Object> userInfoMap = new HashMap<>();
            userInfoMap.put("user_id", user.getId());
            userInfoMap.put("username", user.getUsername());
            redisService.set(uuid.toString().replace("-", ""), userInfoMap);
            StringUtil.createCookie(response, uuid.toString().replace("-", ""), -1);
            returnResult.setObject(user.getUsername());
        }
        returnResult.setStatus(user == null ? Constants.FAIL_STATUS : Constants.SUCCESS_STATUS)
                .setMsg(user == null ? "登录失败" : "登录成功");
        return callback + "(" + gson.toJson(returnResult) + ")";
    }

    @RequestMapping("/logout")
    public String logout(String callback, String fashionid, HttpServletResponse response, HttpServletRequest request) {
        redisService.remove(fashionid);
        ReturnResult returnResult = new ReturnResult();
        boolean isExist = redisService.exists(fashionid);
        StringUtil.deleteCookie(request, response, fashionid);
        returnResult.setStatus(isExist ? Constants.FAIL_STATUS : Constants.SUCCESS_STATUS).setMsg(isExist ? "登出失败" : "");
        return callback + "(" + gson.toJson(returnResult) + ")";
    }

    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest req, HttpServletResponse resp) {

        int width = 100;// 定义图片的width
        int height = 34;// 定义图片的height
        String verifyCode = generateVerifyCode(4);
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("code", verifyCode);
        try {
            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos = resp.getOutputStream();
            outputImage(width, height, sos, verifyCode);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String reqCode) {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        try {
            if (reqCode.equalsIgnoreCase(code)) {
                response.getWriter().print(true);
            } else {
                response.getWriter().print(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 单条短信发送,智能匹配短信模板
     *
     * @param mobile 接收的手机号,仅支持单号码发送
     * @return json格式字符串
     */
    @RequestMapping("/sendSmsCode")

    public String singleSend(String mobile) throws YunpianException {
        String apikey = "3364f8b7e6b8d0b3f32355fae9eb5a2e";
        String phoneCodes = "0123456789";
        String smsCode = VerifyCodeUtils.generateVerifyCode(6, phoneCodes);
        String text = "您的验证码是" + smsCode;
        YunpianRestClient client = new YunpianRestClient(apikey);//用apikey生成client,可作为全局静态变量
        SmsOperator smsOperator = client.getSmsOperator();//获取所需操作类
        ResultDO<SmsSingleSend> result = smsOperator.singleSend(mobile, text);//发送短信,ResultDO<?>.isSuccess()判断是否成功
        return gson.toJson(result);
    }

    @RequestMapping("/getUpToken")
    public String getUpToken(String callback) {
        Map<String, String> result = new HashMap<>();
        Auth auth = Auth.create(Constants.QINIU_ACCESS_KEY, Constants.QINIU_SECRET_KEY);
        result.put("uptoken", auth.uploadToken(Constants.BUCKET_NAME));
        result.put("domain", Constants.QINIU_DOMAIN);
        return callback + "(" + gson.toJson(result) + ")";
    }

}
