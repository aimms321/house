package com.project.house.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.project.house.biz.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2018-04-20.
 */

@Service
public class MailService {
    @Value("${domain.name}")
    private String domain;


    @Value("${spring.mail.username}")
    private String from;



    @Autowired
    private UserMapper userMapper;


    @Autowired
    private JavaMailSender mailSender;
    private final Cache<String, String> registerCache = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterWrite(15, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> removalNotification) {
                    userMapper.deleteByEmail(removalNotification.getValue());
                }
            }).build();

    @Async
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphanumeric(10);
        registerCache.put(randomKey, email);
        String url = domain + "/accounts/verify?key=" + randomKey;
        String title = "房产网激活邮件";
        sendMail(title, url, email);
    }

    @Async
    public void sendMail(String title,String url,String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText(url);
        message.setSubject(title);
        mailSender.send(message);
    }


}
