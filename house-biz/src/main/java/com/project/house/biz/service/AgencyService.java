package com.project.house.biz.service;

import com.project.house.biz.mapper.AgencyMapper;
import com.project.house.biz.mapper.UserMapper;
import com.project.house.common.model.Agency;
import com.project.house.common.model.User;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service



public class AgencyService {

    @Value("${file.prefix}")
    private String fileServerDomain;

    @Value("${file.server.path}")
    private String filePath;

    @Autowired
    private AgencyMapper agencyMapper;

    public User getAgentDetail(Long userId) {

        User user = new User();
        user.setId(userId);
        List<User> users = agencyMapper.selectAgent(user, PageParams.bulid(1, 1));
        if (users.isEmpty()) {
            return null;
        }
        setImg(users);
        User agent = users.get(0);
        Agency agency = new Agency();
        agency.setId(agent.getAgencyId());
        List<Agency> agencyList = agencyMapper.select(agency);
        if (!agencyList.isEmpty()) {
            agent.setAgencyName(agencyList.get(0).getName());
            return agent;
        }
        return agent;

    }


    public void setImg(List<User> users) {
        users.forEach(k->{
            k.setAvatar(fileServerDomain+filePath+k.getAvatar());
        });
    }


    public PageData<User> getAllAgent(Integer pageNum, Integer pageSize) {
        User user = new User();
        user.setType(2);
        PageParams pageParams = PageParams.bulid(pageNum, pageSize);
        List<User> userList = agencyMapper.selectAgent(user, pageParams);
        Long count = agencyMapper.selectAgentCount(user);
        if (!userList.isEmpty()) {
            return PageData.build(userList, pageParams.getPageNum(), pageParams.getPageSize(), count);
        }
        return null;
    }
}
