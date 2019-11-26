package com.hchina.service;


import com.hc.pojo.CarInfo;
import com.hchina.mapper.CarInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarInfoService {
    @Autowired
    private CarInfoMapper carInfoMapper;

    public void addCarInfo(CarInfo carInfo) {
        carInfoMapper.insert(carInfo);
    }


}
