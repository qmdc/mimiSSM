package com.konan.service;

import com.konan.mapper.AdminMapper;
import com.konan.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin adminByID(String name) {
        return adminMapper.adminByID(name);
    }
}
