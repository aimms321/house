package com.project.house.web.controller;

import com.google.common.base.Joiner;
import com.project.house.biz.service.*;
import com.project.house.common.constants.CommonConstants;
import com.project.house.common.model.*;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import com.project.house.common.result.ResultMsg;
import com.project.house.common.utils.BeanHelper;
import com.project.house.web.Interceptor.UserContext;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private CityService cityService;



    @RequestMapping("house/toAdd")
    public String toAdd(ModelMap modelMap) {
        List<City> citys = cityService.getAllCity();
        List<Community> communitys = houseService.getAllCommunity();
        modelMap.put("communitys", communitys);
        modelMap.put("citys", citys);
        return "house/add";
    }

    @RequestMapping("house/add")
    public String addHouse(House house) {
        User user = UserContext.get();
        house.setUserId(user.getId());
        Integer result = houseService.addHouse(house);
        if (result > 0) {
            return "redirect:/house/ownList?" + ResultMsg.successMsg("房产添加成功").asUrlParams();
        }
        return "redirect:/house/ownList?" + ResultMsg.errorMsg("房产添加失败").asUrlParams();

    }

    @RequestMapping("house/ownList")
    public String ownList(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        User user = UserContext.get();
        house.setBookMarked(false);
        house.setUserId(user.getId());
        PageData<House> ps = houseService.queryHouse(house, PageParams.bulid(pageNum, pageSize));
        modelMap.put("ps", ps);
        modelMap.put("pageType", "own");
        return "house/ownlist";
    }

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

    @ResponseBody
    @RequestMapping("house/rating")
    public ResultMsg rating(Double rating,Long id) {
        Integer result = houseService.updateRating(rating, id);
        if (result > 0) {
            return ResultMsg.successMsg("评分成功");
        }
        return ResultMsg.errorMsg("评分失败");

    }

    @ResponseBody
    @RequestMapping("house/bookmark")
    public ResultMsg bookmark(Long id) {
        User user = UserContext.get();
        Integer result = houseService.bindUserToHouse(user.getId(), id, true);
        if (result > 0) {
            return ResultMsg.successMsg("收藏成功");
        }
        return ResultMsg.errorMsg("收藏失败");

    }

    @ResponseBody
    @RequestMapping("house/unbookmark")
    public ResultMsg unBookmark(Long id) {
        User user = UserContext.get();
        Integer result = houseService.unbindUserToHouse(user.getId(), id, true);
        if (result > 0) {
            return ResultMsg.successMsg("删除收藏成功");
        }
        return ResultMsg.errorMsg("删除收藏失败");

    }

    @RequestMapping("house/bookmarked")
    public String bookmarked(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        User user = UserContext.get();
        house.setBookMarked(true);
        house.setUserId(user.getId());
        PageData<House> ps = houseService.queryHouse(house, PageParams.bulid(pageNum, pageSize));
        modelMap.put("ps", ps);
        modelMap.put("pageType", "book");
        return "house/ownlist";
    }

}
