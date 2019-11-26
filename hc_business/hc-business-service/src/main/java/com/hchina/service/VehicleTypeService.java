package com.hchina.service;


import com.hc.pojo.VehicleType;
import com.hchina.mapper.VehicleTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTypeService {
    @Autowired
    private VehicleTypeMapper vehicleTypeMapper;

    public List<VehicleType> queryVehicleType() {
        return vehicleTypeMapper.selectAll();
    }
}
