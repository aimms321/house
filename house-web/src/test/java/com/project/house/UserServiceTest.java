package com.project.house;

import com.project.house.biz.service.UserService;
import com.project.house.common.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.project.house.web.HouseApplication.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testAuth() {
        User user = userService.auth("houseproject@163.com", "111111");
        assert user != null;
//        List<User> users = userService.getUsers();
//        assert !users.isEmpty();
    }
}
