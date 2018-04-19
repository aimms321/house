package com.project.house.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.List;

/**
 * Created by user on 2018-04-16.
 */
@ControllerAdvice
public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    @ExceptionHandler(value={Exception.class})
    public String ValidHandler(HttpServletResponse servletResponse, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(fieldError.getDefaultMessage());
        }
        return "helloAdd";

    }
}
