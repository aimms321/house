package com.project.house.web.controller;

import com.project.house.biz.service.HouseService;
import com.project.house.common.model.House;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping("house/list")
    public String houseList(Integer pageNum, Integer pageSize, ModelMap modelMap, House query) {
        PageData<House> ps = houseService.queryHouse(query, PageParams.bulid(pageNum, pageSize));
        modelMap.put("vo", query);
        modelMap.put("ps", ps);
        return "house/listing";
    }


    public String houseDetail(@Valid Long id, ModelMap modelMap) {
        House house = houseService.queryOneHouse(id);
        if (house.getId() != null && !house.getId().equals(0)) {

        }

    }

}
