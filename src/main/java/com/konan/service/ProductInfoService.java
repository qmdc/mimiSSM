package com.konan.service;

import com.konan.pojo.ProductInfo;
import com.konan.pojo.vo.ProductVo;

import java.util.List;

public interface ProductInfoService {

    //返回全部商品
    List<ProductInfo> getProductInfo();

    //降序返回全部商品
    List<ProductInfo> getProductInfoBySort(int pageNum, int pageSize);

    //添加商品
    int save(ProductInfo productInfo);

    //根据id查询商品
    ProductInfo queryByID(int pid);

    //更新商品
    int updateByID(ProductInfo productInfo);

    //根据id删除商品
    int deleteByID(int pid);

    //批量删除商品
    int deleteBatch(String[] ids);

    //根据条件查询
    List<ProductInfo> condition(ProductVo productVo,int pageNum,int pageSize);
}
