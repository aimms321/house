package com.project.house.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.project.house.biz.mapper.UserMapper;
import com.project.house.common.model.User;
import com.project.house.common.utils.BeanHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                    String email = removalNotification.getValue();
                    User user = new User();
                    user.setEmail(email);
                    List<User> userList = userMapper.selectUsersByQuery(user);
                    if (!userList.isEmpty() && userList.get(0).getEnable() == 0) {
                        userMapper.deleteByEmail(removalNotification.getValue());
                    }

                }
            }).build();

    private final Cache<String, String> resetCache = CacheBuilder
            .newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(100).build();

    @Async
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphanumeric(10);
        registerCache.put(randomKey, email);
        String url = domain + "/accounts/verify?key=" + randomKey;
        String title = "房产网激活邮件";
        sendMail(title, url, email);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void sendMail(String title,String url,String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText(url);
        message.setSubject(title);
        mailSender.send(message);
    }


    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        User user = new User();
        user.setEnable(1);
        user.setEmail(email);
        BeanHelper.onUpdate(user);
        userMapper.updateUser(user);
        registerCache.invalidate(key);
        return true;
    }

    public void resetNotify(String username) {
        String randomKey = RandomStringUtils.randomAlphanumeric(10).toString();
        resetCache.put(randomKey, username);
        String url = domain + "/accounts/reset?resetKey=" + randomKey;
        sendMail("房产网重置密码邮件", url, username);
    }

    public String findResetKey(String resetKey) {
        return resetCache.getIfPresent(resetKey);
    }

}
