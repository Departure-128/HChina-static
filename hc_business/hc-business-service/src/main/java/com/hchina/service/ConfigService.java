package com.hchina.service;


import com.hc.pojo.Config;
import com.hchina.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ConfigService {
    @Autowired
    private ConfigMapper configMapper;

    public List<Config> queryConfig(Long type) {
        Example example = new Example(Config.class);
        example.createCriteria().andEqualTo("type",type);
        List<Config> configs = configMapper.selectByExample(example);
        return configs;
    }
}
