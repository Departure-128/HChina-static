package com.hchina.service;


import com.hc.pojo.GoodsType;
import com.hchina.mapper.GoodsTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsTypeService {
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    public List<GoodsType> queryGoodsType() {
        return goodsTypeMapper.selectAll();
    }
}
