package com.konan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.konan.mapper.ProductInfoMapper;
import com.konan.pojo.ProductInfo;
import com.konan.pojo.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    public static PageInfo<ProductInfo> pageInfo=null;

    @Override
    public List<ProductInfo> getProductInfo() {
        return productInfoMapper.getProductInfo();
    }

    @Override
    public List<ProductInfo> getProductInfoBySort(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProductInfo> list = productInfoMapper.getProductInfoBySort();
        pageInfo = new PageInfo<>(list);
        return list;
    }

    @Override
    public int save(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public ProductInfo queryByID(int pid) {
        return productInfoMapper.queryByID(pid);
    }

    @Override
    public int updateByID(ProductInfo productInfo) {
        return productInfoMapper.updateByID(productInfo);
    }

    @Override
    public int deleteByID(int pid) {
        return productInfoMapper.deleteByID(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> condition(ProductVo productVo,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProductInfo> list = productInfoMapper.condition(productVo);
        pageInfo = new PageInfo<>(list);
        return list;
    }
}
