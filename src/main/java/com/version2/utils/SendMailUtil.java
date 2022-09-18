package com.version2.utils;


import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class SendMailUtil {

    public static String sendMail(String mailAdr, int len) throws MessagingException {
        String code = generateCode(len);
        try {
            SimpleEmail mail = new SimpleEmail();
            // 发送邮件的服务器
            mail.setHostName("smtp.qq.com");
            // 刚刚记录的授权码，是开启SMTP的密码
            mail.setAuthentication("869083577@qq.com", "zsfqksfexfgebbbi");
            // 发送邮件的邮箱和发件人
            mail.setFrom("869083577@qq.com", "KallenBlue");
            // 使用安全链接
            mail.setSSLOnConnect(true);
            // 接收的邮箱
            mail.addTo(mailAdr);
            // 邮件的主题
            mail.setSubject("注册验证码");
            // 邮件的内容
            mail.setMsg("验证码为:" +code);
            // 发送
            mail.send();
            System.out.println("发送成功");
            return code;
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String generateCode(int len) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < len; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return String.valueOf(stringBuilder);
    }
}
