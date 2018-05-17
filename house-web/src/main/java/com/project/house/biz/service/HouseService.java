package com.project.house.biz.service;

import com.project.house.biz.mapper.HouseMapper;
import com.project.house.common.model.House;
import com.project.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {

    @Autowired
    private HouseMapper houseMapper;


    public void queryHouse(House query, PageParams bulid) {
        houseMapper.selectPageHouses(query,bulid);
    }
}
