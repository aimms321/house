package com.project.house.web.controller;

import com.project.house.biz.service.HouseService;
import com.project.house.common.model.House;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping("house/list")
    public String houseList(Integer pageNum, Integer pageSize, ModelMap modelMap, House query) {
        PageData<House> ps = houseService.queryHouse(query, PageParams.bulid(pageNum, pageSize));
        modelMap.put("vo", query);
        modelMap.put("ps", ps);
        System.out.println(ps);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(ps.getPagination().getTotalCount());
        return "house/listing";
    }


}
