package com.project.house.biz.mapper;

import com.project.house.common.model.Agency;
import com.project.house.common.model.User;
import com.project.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgencyMapper {

    List<User> selectAgent(@Param("user") User user, @Param("pageParams") PageParams bulid);

    List<Agency> select(Agency agency);
}

