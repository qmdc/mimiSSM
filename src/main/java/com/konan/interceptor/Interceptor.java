package com.konan.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        // 如果是登陆页面则放行
        if (request.getRequestURI().contains("/Admin/addlogin.do")||request.getRequestURI().contains("/Admin/login.do")) {
            return true;
        }

        HttpSession session = request.getSession();

        // 如果用户已登陆也放行
        if(session.getAttribute("username") != null) {
            return true;
        }

        // 用户没有登陆跳转到登陆页面
        //request.getRequestDispatcher("/index.jsp").forward(request, response);
        System.out.println("===========---------------");
        response.sendRedirect("/index.jsp");
        return false;
    }
}
