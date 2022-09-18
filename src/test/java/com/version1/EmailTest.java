package com.version1;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.jupiter.api.Test;

public class EmailTest {
    @Test
    public void emailTest(){
        String mailAdr = "869083577@qq.com";
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
            mail.setMsg("验证码为:" +5645);
            // 发送
            mail.send();
            System.out.println("发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
