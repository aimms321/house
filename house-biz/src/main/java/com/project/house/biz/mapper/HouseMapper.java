package com.project.house.biz.mapper;

import com.project.house.common.model.Community;
import com.project.house.common.model.House;
import com.project.house.common.model.HouseUser;
import com.project.house.common.model.UserMsg;
import com.project.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMapper {

    List<House> selectPageHouses(@Param("house") House query, @Param("pageParams") PageParams bulid);

    Long selectPageCount(@Param("house") House query);

    List<Community> selectCommunity(Community community);

    HouseUser selectSaleHouseUser(Long id);

    int insertUserMsg(UserMsg userMsg);

    int insertHouse(House house);

    int insertHouseUser(HouseUser houseUser);

    int updateHouse(House query);

    int deleteHouseUser(HouseUser houseUser);
}
