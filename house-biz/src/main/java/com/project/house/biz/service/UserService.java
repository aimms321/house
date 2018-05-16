package com.project.house.biz.service;


import com.google.common.collect.Lists;
import com.project.house.biz.mapper.UserMapper;
import com.project.house.common.model.User;

import com.project.house.common.utils.BeanHelper;
import com.project.house.common.utils.HashUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.ServletContext;
import java.util.List;


@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Value("${file.prefix}")
    private String imgPrefix;



    public List<User> getUsers() {

        return userMapper.selectUsers();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
        List<String> imgList = fileService.uploadAndGetImgPaths(Lists.newArrayList(user.getAvatarFile()));
        if (!imgList.isEmpty()) {
            user.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(user, User.class);
        BeanHelper.onInsert(user);
        user.setEnable(0);
        mailService.registerNotify(user.getEmail());
        userMapper.insertUser(user);
        return true;
    }

    public User auth(String username, String password) {
        User user = new User();
        user.setEmail(username);
        user.setPasswd(HashUtils.encryPassword(password));
        user.setEnable(1);
        List<User> users = selectUsersByQuery(user);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    public List<User> selectUsersByQuery(User user) {
        List<User> users = userMapper.selectUsersByQuery(user);
        users.forEach(k->{
            k.setAvatar(imgPrefix+k.getAvatar());
        });
        return users;
    }

    public boolean checkIfUserExist(String email) {
        User user = new User();
        user.setEmail(email);
        List<User> users = userMapper.selectUsersByQuery(user);
        if (users.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }


    public int updateUser(User user) {
        BeanHelper.onUpdate(user);
        return userMapper.updateUser(user);
    }
}
