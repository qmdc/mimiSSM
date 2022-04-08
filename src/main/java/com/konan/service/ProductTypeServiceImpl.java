package com.konan.service;

import com.konan.pojo.ProductType;
import com.konan.mapper.ProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAllType() {
        List<ProductType> allType = productTypeMapper.getAllType();
        return allType;
    }
}
