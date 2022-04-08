package com.konan.mapper;

import com.konan.pojo.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {

    //通过name查询用户判断登录
    Admin adminByID(@Param("name") String name);

}