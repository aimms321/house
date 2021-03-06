package com.project.house.web.controller;

import com.project.house.biz.service.MailService;
import com.project.house.biz.service.UserService;
import com.project.house.common.constants.CommonConstants;
import com.project.house.common.dto.ChangePasswdDto;
import com.project.house.common.dto.ProfileDto;
import com.project.house.common.dto.ResetDto;
import com.project.house.common.model.User;
import com.project.house.common.result.ResultMsg;
import com.project.house.common.utils.BeanHelper;
import com.project.house.common.utils.HashUtils;
import com.project.house.web.Interceptor.UserContext;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
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
import java.rmi.server.RMIClassLoader;
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
        boolean result = mailService.enable(key);
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
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
        if (StringUtils.isBlank(target)) {
            return "redirect:/index";
        } else {
            return "redirect:" + target;
        }

    }

    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request) {
        String target = request.getParameter("target");
        HttpSession session = request.getSession(true);
        session.invalidate();
        if (StringUtils.isBlank(target)) {
            return "redirect:/index?" + ResultMsg.successMsg("登出成功！").asUrlParams();
        }
        return "redirect:" + target + "?" + ResultMsg.successMsg("登出成功！").asUrlParams();
    }

    @RequestMapping("accounts/remember")
    public String remember(String username, ModelMap modelMap) {
        if (StringUtils.isBlank(username)) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("请输入用户名").asUrlParams();
        }
        boolean isExist = userService.checkIfUserExist(username);
        if (isExist) {
            mailService.resetNotify(username);
            modelMap.put("email", username);
            return "user/accounts/remember";
        } else {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("Email不存在").asUrlParams();
        }
    }

    @RequestMapping("accounts/reset")
    public String reset(String resetKey, ModelMap modelMap) {
        if (StringUtils.isEmpty(resetKey)) {
            return "user/accounts/login";
        }
        String email = mailService.findResetKey(resetKey);
        if (email == null) {
            return "redirect:/accounts/login?" + ResultMsg.errorMsg("验证码无效或过期").asUrlParams();
        }
        modelMap.put("email", email);
        modelMap.put("success_key", resetKey);
        return "user/accounts/reset";
    }

    @RequestMapping("accounts/resetSubmit")
    public String resetSubmit(@Valid ResetDto resetDto) {
        String email = mailService.findResetKey(resetDto.getKey());
        if (email == null) {
            return "redirect:/accounts/login?" + ResultMsg.errorMsg("验证码无效或过期").asUrlParams();
        }
        User user = new User();
        user.setEmail(resetDto.getEmail());
        user.setPasswd(HashUtils.encryPassword(resetDto.getPasswd()));
        int result = userService.updateUser(user);
        if (result > 0) {
            return "redirect:/index?" + ResultMsg.successMsg("密码重置成功").asUrlParams();
        } else {
            return "redirect:/index?" + ResultMsg.errorMsg("密码重置成功").asUrlParams();
        }

    }

    @RequestMapping("accounts/profile")
    public String profile(@Valid ProfileDto profileDto,HttpServletRequest request) {
        if (profileDto.getEmail()==null) {
            return "user/accounts/profile";
        }
        User user = UserContext.get();
        if (user == null) {
            return "/index";
        }
        String email = user.getEmail();
        User updateUser = new User();
        updateUser.setName(profileDto.getName());
        updateUser.setPhone(profileDto.getPhone());
        updateUser.setAboutme(profileDto.getAboutme());
        updateUser.setEmail(email);
        int result = userService.updateUser(updateUser);
        if (result > 0) {
            User query = new User();
            user.setEmail(user.getEmail());
            List<User> users = userService.selectUsersByQuery(user);
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,users.get(0));
            return "redirect:/accounts/profile?" +
                    ResultMsg.successMsg("个人信息更新成功").asUrlParams();
        } else {
            return "redirect:/accounts/profile?" +
                    ResultMsg.errorMsg("个人信息更新失败").asUrlParams();
        }

    }

    @RequestMapping("/accounts/changePassword")
    public String changePassword(@Valid ChangePasswdDto changePasswdDto,HttpServletRequest request) {
        String target = request.getRequestURI().toString();
        if (StringUtils.isBlank(changePasswdDto.getEmail())) {
            return "redirect:/"+target;
        }
        String email = changePasswdDto.getEmail();
        String password = changePasswdDto.getPassword();
        String newPassword = changePasswdDto.getNewPassword();
        User user = userService.auth(email,password);
        if (user == null) {
            return "redirect:/accounts/profile?"+ResultMsg.errorMsg("原密码错误").asUrlParams();
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPasswd(HashUtils.encryPassword(newPassword));
        int result = userService.updateUser(newUser);
        if (result > 0) {
            return "redirect:/accounts/profile?"+ResultMsg.successMsg("密码更新成功！").asUrlParams();
        } else {
            return "redirect:/accounts/profile?"+ ResultMsg.errorMsg("密码更新失败").asUrlParams();
        }
    }
}
