package com.konan.mapper;

import com.konan.pojo.ProductType;

import java.util.List;

public interface ProductTypeMapper {

    //查询所有商品类型
    List<ProductType> getAllType();

}