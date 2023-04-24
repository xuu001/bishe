package xyz.hhang.boot.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailService {
    @Value("${mail.username}")
    private String mailUsername;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送注册成功邮件
     * @param to 收件人
     */
    public void sendRegisterMail(String to){
        //新的进程发邮件
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(new Runnable() {
            @Override
            public void run() {
                // 发送带html的邮件
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = null;
                try {
                    // freemarker
                    mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
                    String htmlContent = "<div align=\"center\">" +
                            "<img src=\" https://www.hualigs.cn/image/60812d1936ea3.jpg \" />" +
                            "<br>你好!欢迎加入企业疫情防控管理系统<br>" +
                            "[本邮件由系统自动发送，请勿直接回复]<br> 点击<a href=\"http://broke.hhang.xyz\">登录</a>" +
                            "<br>" +
                            "<br>" +
                            "<h4>企业疫情防控管理系统运营团队</h4>" +
                            "</div>";
                    message.setContent(htmlContent, "text/html;charset=utf-8");
                    mimeMessageHelper.setFrom(mailUsername);
                    mimeMessageHelper.setTo(to);
                    mimeMessageHelper.setSubject("[HHANGBroke]注册成功通知");

                    javaMailSender.send(message);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 发送重置密码邮件
     * @param to 收件人
     */
    public void sendResetMail(String to, String code){
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(new Runnable() {
            @Override
            public void run() {
                // 发送带html的邮件
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = null;
                try {
                    // freemarker
                    mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
                    String htmlContent = "<div align=\"center\"><img src=\"https://www.hualigs.cn/image/60812d1936ea3.jpg \" alt=\"logo\"/>" +
                            "<br>你好:<br/><br/>\n"+"你的验证码是" +code+" <br>" +
                            "<br>" +
                            "<br>" +
                            "<h4>HHANGBroke运营团队</h4>" +
                            "</div>";
                    message.setContent(htmlContent, "text/html;charset=utf-8");
                    mimeMessageHelper.setFrom(mailUsername);
                    mimeMessageHelper.setTo(to);
                    mimeMessageHelper.setSubject("[HHANGBroke]验证码发送通知");

                    javaMailSender.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

