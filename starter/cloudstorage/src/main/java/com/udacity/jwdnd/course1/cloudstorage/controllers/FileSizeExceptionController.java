package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileSizeExceptionController implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof MaxUploadSizeExceededException) {
            modelAndView.addObject("message", "File too large to upload (Max file size is 100 MB");
            modelAndView.addObject("success", false);
            modelAndView.setViewName("result");
            return modelAndView;
        }
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
