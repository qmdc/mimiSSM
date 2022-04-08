package com.konan.controller;

import com.konan.pojo.Admin;
import com.konan.service.AdminService;
import com.konan.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/Admin")
public class AdminControl {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/addlogin.do")
    public String addlogin() {
        return "login";
    }

    @RequestMapping("/login.do")
    public String login(String name, String pwd, HttpServletRequest request, HttpSession session) {
//        System.out.println("-----------------");
//        System.out.println(name+":"+pwd);

        if (session.getAttribute("username")==name&&session.getAttribute("password")==pwd) {
            return "main";
        }
        if (name!=null&&pwd!=null) {
            Admin admin = adminService.adminByID(name);

//            System.out.println(admin);
//            System.out.println(session.getAttribute("username"));

            if (admin!=null) {
                if(admin.getaPass().equals(MD5Util.getMD5(pwd))){
//                    System.out.println(MD5Util.getMD5(pwd));
                    request.setAttribute("name",admin.getaName());
                    session.setAttribute("username",name);
                    session.setAttribute("password",pwd);
                    System.out.println(session.getAttribute("username"));
                    return "main";
                }
                request.setAttribute("errmsg","用户名或密码错误！");
            }
            request.setAttribute("errmsg","用户名或密码错误！");
            return "login";
        }
        return "login";
    }
}
