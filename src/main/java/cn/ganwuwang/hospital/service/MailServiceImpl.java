package cn.ganwuwang.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class MailServiceImpl {

    @Autowired
    private JavaMailSenderImpl mailSender;//注入邮件工具类

    @Autowired
    private RedisTemplate redisTemplate;

    private static String from = "1101543410@qq.com";

    public Boolean checkMail(String to, Integer check){
        Integer old = (Integer) redisTemplate.opsForValue().get(to);
        if(old != null && check.equals(old)){
            return true;
        }
        return false;
    }

    public void sendCheckMail(String to){
        Integer check = (int)((Math.random()*9+1)*1000);
        String subject = "智能分诊平台邮箱验证码";
        String content = "验证码为："+check+",有效时间为60秒。";
        sendSimpleMail(to, subject, content);
        redisTemplate.opsForValue().set(to,check, 60 , TimeUnit.SECONDS);
    }

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content){
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

}
