package com.project.house.biz.service;

import com.project.house.biz.mapper.HouseMapper;
import com.project.house.common.model.House;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    @Autowired
    private HouseMapper houseMapper;


    public PageData<House> queryHouse(House query, PageParams bulid) {

        List<House> houses = houseMapper.selectPageHouses(query, bulid);
        if (houses.isEmpty()) {
            return null;
        }

    }
}
