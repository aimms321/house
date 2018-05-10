package com.project.house.web.controller;

import com.project.house.biz.service.MailService;
import com.project.house.biz.service.UserService;
import com.project.house.common.constants.CommonConstants;
import com.project.house.common.model.User;
import com.project.house.common.result.ResultMsg;
import com.project.house.common.utils.HashUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping("user/accounts/register")
    public String registerPage(ModelMap modelMap) {
        return "user/accounts/register";
    }

    @RequestMapping("accounts/register")
    public String accountsRegister(@Valid User user, ModelMap modelMap, BindingResult bindingResult) {
        if (user == null || bindingResult.hasErrors()) {
            return "user/accounts/register";
        }
        if (userService.addUser(user)) {
            modelMap.put("email", user.getEmail());
            return "/user/accounts/registerSubmit";
        }
        return "user/accounts/register";

    }

    @RequestMapping("accounts/verify")
    public String verify(String key) {
        if (StringUtils.isEmpty(key)) {
            return "user/accounts/register";
        }
        boolean result=mailService.enable(key);
        if (result) {
            return "homepage/index";
        } else {
            return "user/accounts/register";
        }
    }

    @RequestMapping("accounts/signin")
    public String signin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");
        if (username == null || password == null) {
            request.setAttribute("target", target);
            return "user/accounts/signin";
        }
        User user = userService.auth(username, password);
        if (user == null) {
            return "redirect:/accounts/signin?" + "target=" + target + "&username=" + username + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
            return StringUtils.isNotBlank(target) ? "redirect"+target : "redirect:/index";
        }

    }
}
