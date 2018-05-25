package com.project.house.web.controller;

import com.project.house.biz.service.AgencyService;
import com.project.house.biz.service.HouseService;
import com.project.house.biz.service.UserService;
import com.project.house.common.model.House;
import com.project.house.common.model.HouseUser;
import com.project.house.common.model.User;
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

    @Autowired
    private UserService userService;

    @Autowired
    private AgencyService agencyService;

    @RequestMapping("house/list")
    public String houseList(Integer pageNum, Integer pageSize, ModelMap modelMap, House query) {
        PageData<House> ps = houseService.queryHouse(query, PageParams.bulid(pageNum, pageSize));
        modelMap.put("vo", query);
        modelMap.put("ps", ps);
        return "house/listing";
    }


    @RequestMapping("house/detail")
    public String houseDetail(@Valid Long id, ModelMap modelMap) {
        House house = houseService.queryOneHouse(id);
        if (house.getId() != null && !house.getId().equals(0)) {
            HouseUser houseUser = houseService.getHouseUser(id);
            User agent = agencyService.getAgentDetail(houseUser.getUserId());
            modelMap.put("agent", agent);
        }
        modelMap.put("house", house);

        return "house/detail";
    }

}
