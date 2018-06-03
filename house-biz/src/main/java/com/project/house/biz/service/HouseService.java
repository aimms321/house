package com.project.house.biz.service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.project.house.biz.mapper.HouseMapper;
import com.project.house.common.constants.CommonConstants;
import com.project.house.common.constants.HouseUserType;
import com.project.house.common.model.*;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import com.project.house.common.utils.BeanHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {

    @Value("${file.prefix}")
    private String fileServerDomain;

    @Value("${file.server.path}")
    private String filePath;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private MailService mailService;

    @Autowired
    private FileService fileService;


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
            k.setFirstImg(fileServerDomain+filePath +k.getFirstImg());
            k.setImageList(k.getImageList().stream().map(img -> fileServerDomain +filePath+ img).collect(Collectors.toList()));
            k.setFloorPlanList(k.getFloorPlanList().stream().map(img -> fileServerDomain +filePath+ img).collect(Collectors.toList()));
        });
        return houses;
    }

    public House queryOneHouse(Long id) {
        House house = new House();
        house.setId(id);
        List<House> houses = queryAndSetImg(house, PageParams.bulid(1, 1));
        if (!houses.isEmpty()) {
            return houses.get(0);
        }
        return null;
    }

    public HouseUser getHouseUser(Long id) {
        return houseMapper.selectSaleHouseUser(id);
    }

    public void addUserMsg(UserMsg userMsg) {
        BeanHelper.onInsert(userMsg);
        houseMapper.insertUserMsg(userMsg);
        User user = agencyService.getAgentDetail(userMsg.getAgentId());
        mailService.sendMail("来自用户"+userMsg.getEmail()+"的邮件",userMsg.getMsg(),user.getEmail());
    }

    public List<Community> getAllCommunity() {
        Community community = new Community();
        return houseMapper.selectCommunity(community);
    }

    public Integer addHouse(House house) {
        house.setState(CommonConstants.HOUSE_STATE_UP);
        if (CollectionUtils.isNotEmpty(house.getHouseFiles())) {
            String images = Joiner.on(",").join(fileService.uploadAndGetImgPaths(house.getHouseFiles()));
            house.setImages(images);
        }
        if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())) {
            String images = Joiner.on(",").join(fileService.uploadAndGetImgPaths(house.getFloorPlanFiles()));
            house.setImages(images);
        }
        BeanHelper.onInsert(house);
        int result = houseMapper.insertHouse(house);
        Integer bindResult = bindUserToHouse(house.getUserId(), house.getId(), false);
        return bindResult;
    }

    public Integer bindUserToHouse(Long userId, Long houseId,boolean collect) {
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(houseId);
        houseUser.setUserId(userId);
        houseUser.setType(collect? HouseUserType.BOOKMARK.value:HouseUserType.SALE.value);
        BeanHelper.onInsert(houseUser);
        int result = houseMapper.insertHouseUser(houseUser);
        return result;
    }

    public Integer updateRating(Double rating, Long id) {
        House query = queryOneHouse(id);
        Double oldRating = query.getRating();
        query.setRating(oldRating.equals(0D)?rating:Math.min(5,(query.getRating()+rating)/2));
        BeanHelper.onUpdate(query);
        return houseMapper.updateHouse(query);

    }

    public Integer unbindUserToHouse(Long userId, Long houseId, boolean collect) {
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(houseId);
        houseUser.setUserId(userId);
        houseUser.setType(collect? HouseUserType.BOOKMARK.value:HouseUserType.SALE.value);
        BeanHelper.onInsert(houseUser);
        int result = houseMapper.deleteHouseUser(houseUser);
        return result;

    }
}
