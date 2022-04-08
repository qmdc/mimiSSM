package com.konan.mapper;

import com.konan.pojo.ProductInfo;
import com.konan.pojo.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoMapper{

    //返回全部商品
    List<ProductInfo> getProductInfo();

    //降序desc返回商品
    List<ProductInfo> getProductInfoBySort();

    //添加商品
    int insert(ProductInfo productInfo);

    //根据id查询商品
    ProductInfo queryByID(@Param("id") int pid);

    //更新商品
    int updateByID(ProductInfo productInfo);

    //根据id删除商品
    int deleteByID(@Param("id") int pid);

    //批量删除商品
    int deleteBatch(String[] ids);

    //条件查询
    List<ProductInfo> condition(ProductVo productVo);

}