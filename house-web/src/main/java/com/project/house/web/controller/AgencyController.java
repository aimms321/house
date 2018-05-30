package com.project.house.web.controller;

import com.project.house.biz.service.AgencyService;
import com.project.house.biz.service.HouseService;
import com.project.house.common.model.House;
import com.project.house.common.model.User;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private HouseService houseService;

    @RequestMapping("/agency/agentList")
    public String agentList(ModelMap modelMap, Integer pageNum, Integer pageSize) {
        if (pageSize == null) {
            pageSize = 6;
        }
        PageData<User> ps = agencyService.getAllAgent(pageNum, pageSize);
        modelMap.put("ps", ps);
        return "user/agent/agentList";
    }


    @RequestMapping("/agency/agentDetail")
    public String agentDetail(Long id, ModelMap modelMap,House vo) {
        User agent = agencyService.getAgentDetail(id);
        if (agent != null) {
            modelMap.put("agent", agent);
        }
        House query = new House();
        query.setUserId(agent.getId());
        query.setBookMarked(false);
        PageData<House> bindHouses = houseService.queryHouse(query, PageParams.bulid(1, 3));
        if (bindHouses != null) {
            modelMap.put("bindHouses", bindHouses.getList());
        }
        modelMap.put("bindHouses", bindHouses.getList());
        modelMap.put("vo", vo);
        return "user/agent/agentDetail";

    }




}
