package com.project.house.web.controller;

import com.project.house.biz.service.UserService;
import com.project.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    public String accountsRegister(@RequestBody @Valid User user, ModelMap modelMap, BindingResult bindingResult) {
        if (user == null||bindingResult.hasErrors()) {
            return "user/accounts/register";
        }
        if (userService.addUser(user)) {
            modelMap.put("email", user.getEmail());
            return "/user/accounts/registerSubmit";
        }
        return "user/accounts/register";

    }


}
