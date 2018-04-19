package com.project.house.biz.mapper;


import com.project.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {
    public List<User> selectUsers();

    int insertUser(User user);
}
