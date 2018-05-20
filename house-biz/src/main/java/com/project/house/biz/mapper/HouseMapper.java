package com.project.house.biz.mapper;

import com.project.house.common.model.House;
import com.project.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMapper {

    List<House> selectPageHouses(@Param("house") House query, @Param("pageParams") PageParams bulid);
}
