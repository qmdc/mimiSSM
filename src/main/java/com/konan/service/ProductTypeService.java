package com.konan.service;

import com.konan.pojo.ProductType;

import java.util.List;

public interface ProductTypeService {

    //查询所有商品类型
    List<ProductType> getAllType();
}
