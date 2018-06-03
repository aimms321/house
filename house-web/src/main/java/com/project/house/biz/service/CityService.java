package com.project.house.biz.service;

import com.google.common.collect.Lists;
import com.project.house.biz.mapper.CityMapper;
import com.project.house.common.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CityService {


    public List<City> getAllCity() {
        City city = new City();
        city.setCityCode("100000");
        city.setCityName("北京");
        city.setId(1);
        return Lists.newArrayList(city);
    }
}
