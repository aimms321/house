package com.project.house.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.project.house.biz.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2018-04-20.
 */

@Service
public class MailService {
    @Value("${domain.name}")
    private String domain;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    private final Cache<String, String> registerCache = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterWrite(15, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> removalNotification) {
                    userMapper.deleteByEmail(removalNotification.getValue());
                }
            }).build();

    private void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphanumeric(10);
        registerCache.put(randomKey, email);
        String url = domain + "account/verify?key=" + randomKey;
    }

    public void sendMail() {
        new SampleMailMessage
    }

}
