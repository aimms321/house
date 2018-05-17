package com.project.house.biz.mapper;

import com.project.house.common.model.House;
import com.project.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseMapper {

    List<House> selectPageHouses(House query, PageParams bulid);
}
