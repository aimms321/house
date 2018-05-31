package com.project.house.web.controller;

import com.project.house.biz.service.*;
import com.project.house.common.constants.CommonConstants;
import com.project.house.common.model.*;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import com.project.house.common.result.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RecommendService recommendService;

    @RequestMapping("house/list")
    public String houseList(Integer pageNum, Integer pageSize, ModelMap modelMap, House query) {
        PageData<House> ps = houseService.queryHouse(query, PageParams.bulid(pageNum, pageSize));
        List<House> recomHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", recomHouses);
        modelMap.put("vo", query);
        modelMap.put("ps", ps);
        return "house/listing";
    }


    @RequestMapping("house/detail")
    public String houseDetail(long id, ModelMap modelMap) {
        House house = houseService.queryOneHouse(id);
        if (house.getId() != null && !house.getId().equals(0)) {
            HouseUser houseUser = houseService.getHouseUser(id);
            recommendService.increase(id);
            User agent = agencyService.getAgentDetail(houseUser.getUserId());
            modelMap.put("agent", agent);
        }
        List<House> recomHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", recomHouses);
        modelMap.put("house", house);
        List<Comment> commentList = commentService.getHouseComment(id,8);
        modelMap.put("commentList", commentList);


        return "house/detail";
    }

    @RequestMapping("house/leaveMsg")
    public String leaveMsg(UserMsg userMsg) {
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id="+userMsg.getHouseId()+ ResultMsg.successMsg("邮件发送成功").asUrlParams();
    }

}
