package com.project.house.web.controller;

import com.google.common.collect.Lists;
import com.project.house.biz.service.UserService;
import com.project.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "hello")
    public String hello(ModelMap modelMap) {
        List<User> users = userService.getUsers();
        User userOne = users.get(0);
        modelMap.put("user", userOne);
        return "hello";
    }

    @GetMapping(path = "index")
    public String index() {
        return "homepage/index";
    }

    @PostMapping(path = "hello/add")
    public String helloAdd(@RequestBody @Valid User user, BindingResult bindingResult,ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errorList = Lists.newLinkedList();
            for (FieldError fieldError : fieldErrors) {
                errorList.add(fieldError.getDefaultMessage());
            }
            modelMap.put("errorList", errorList);
            return "validTest";
        }
        int result = userService.addUser(user);
        if (result > 0) {
            modelMap.put("user", user);
        }
        return "helloAdd";

    }
}
