package xyz.hhang.boot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@ComponentScan(basePackages = "xyz.hhang.boot.service")
@MapperScan(basePackages = "xyz.hhang.boot.dao")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class AppConfig {

    //=====发邮箱配置
    @Value("${mail.encode}")
    private String mailEncode;

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.userpassword}")
    private String mailPassword;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding(mailEncode);
        javaMailSender.setHost(mailHost);
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);
        return javaMailSender;
    }
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        // 单个文件大小
        commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 50);
        // 缓冲区大小
        commonsMultipartResolver.setMaxInMemorySize(1024 * 1024 * 4);
        return commonsMultipartResolver;
    }
}
