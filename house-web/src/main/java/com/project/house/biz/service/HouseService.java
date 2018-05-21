package com.project.house.biz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.project.house.biz.mapper.HouseMapper;
import com.project.house.common.model.Community;
import com.project.house.common.model.House;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {

    @Value("${file.prefix}")
    private String imgPrefix;

    @Autowired
    private HouseMapper houseMapper;



    public PageData<House> queryHouse(House query, PageParams pageParams) {
        List<House> houses = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(query.getName())) {
            Community community = new Community();
            community.setName(query.getName());
            List<Community> communityList = houseMapper.selectCommunity(community);
            if (!communityList.isEmpty()) {
                query.setCommunityId(communityList.get(0).getId());
            }
        }
        houses = queryAndSetImg(query, pageParams);
        Long count = houseMapper.selectPageCount(query);
        return PageData.build(houses, pageParams.getPageNum(), pageParams.getPageSize(), count);
    }

    public List<House> queryAndSetImg(House query, PageParams pageParams) {
        List<House> houses = houseMapper.selectPageHouses(query, pageParams);
        houses.forEach(k->{
            k.setFirstImg(imgPrefix+k.getFirstImg());
            k.setImageList(k.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            k.setFloorPlanList(k.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
        });
        return houses;
    }
}
