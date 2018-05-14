package com.project.house.web.Interceptor;

import com.project.house.common.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Component
public class AuthActionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        User user = UserContext.get();
        if (user == null) {
            String target = URLEncoder.encode(httpServletRequest.getRequestURL().toString(), "utf-8");
            String errorMsg = URLEncoder.encode("请登录后操作", "utf-8");
            if ("GET".equalsIgnoreCase(httpServletRequest.getMethod())) {
                httpServletResponse.sendRedirect("/accounts/signin?target=" + target + "&errorMsg=" + errorMsg);
                return false;
            } else {
                httpServletResponse.sendRedirect("/accounts/signin?errorMsg=" + errorMsg);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
