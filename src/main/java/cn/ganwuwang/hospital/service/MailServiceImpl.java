package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Register;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

    public void sendCheckMail(String to) throws GlobalException {

        if(redisTemplate.hasKey(to)){
            throw new GlobalException(ResultEnum.CHECK_TIME_ERROR);
        }
        Integer check = (int)((Math.random()*9+1)*1000);
        String subject = "智能分诊平台邮箱验证码";
        String content = "验证码为："+check+",有效时间为120秒。";
        sendSimpleMail(to, subject, content);
        System.out.println(check);
        redisTemplate.opsForValue().set(to,check, 120 , TimeUnit.SECONDS);
    }

    public void sendRegister(String to, Register register, Integer num){


        String scope = null;
        if(register.getScope() == 0){
            scope = "9:00-12:00";
        }else {
            scope = "14:00-18:00";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(register.getDate());

        String subject = "智能分诊平台挂号提示";
        String content = "用户："+register.getUserName()+",您已成功在"+dateString+"日"+scope+"时间段挂号"+register.getDoctorName()+"医生," +
                "\n挂号号码为："+register.getId()+",您为该时段第"+num+"名看诊用户，请耐心等待。";
        sendSimpleMail(to, subject, content);

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
