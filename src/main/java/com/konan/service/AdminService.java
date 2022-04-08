package com.konan.service;

import com.konan.pojo.Admin;

public interface AdminService {

    //通过id查询用户判断登录
    Admin adminByID(String name);

}
