package com.course.springfood.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("X-SpringFood-Deprecated",
                    "Essa versão da API está depreciada e deixará de existir a partir de xx/xx/xxxx."
                            + "Use a versão mais atual da API.");
        }

        return true;
    }

}