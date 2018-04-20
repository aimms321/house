package com.project.house.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.project.house.biz.mapper.UserMapper;
import com.project.house.common.model.User;

import com.project.house.common.utils.BeanHelper;
import com.project.house.common.utils.HashUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {



    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;




    public List<User> getUsers() {

        return userMapper.selectUsers();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
        List<String> imgList = fileService.getImgPaths(Lists.newArrayList(user.getAvatarFile()));
        if (!imgList.isEmpty()) {
            user.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(user, User.class);
        BeanHelper.onInsert(user);
        user.setEnable(0);
        registerNotify(user.getEmail());
        userMapper.insertUser(user);
    }



}
