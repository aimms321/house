package com.project.house.common.model;


import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;

public class House {

    //主键id
    @NotNull
    private Long id;

    //房产名称
    private String name;

    //1-销售 2-出租
    private Integer type;

    //单位元，价格
    private Long price;

    //房屋图片
    private String images;

    //面积
    private Long area;

    //卧室的数量
    private Long beds;

    //卫生间的数量
    private Long baths;

    //评分
    private Double rating;

    //房产的描述
    private String remarks;

    //房产属性
    private String properties;

    //户型图
    private String floorPlan;

    //标签
    private String tags;

    //创建时间
    private Long createTime;

    //城市ID
    private Long cityId;

    //小区ID
    private Long communityId;

    //房产的地址
    private String address;

    //1-上架 2-下架
    private Integer state;

    private Integer roundRating;

    private String firstImg;

    private String priceStr;

    private String typeStr;

    private List<String> imageList = Lists.newArrayList();

    private List<String> floorPlanList = Lists.newArrayList();

    private List<MultipartFile> houseFiles;

    private List<MultipartFile> floorPlanFiles;

    private Boolean bookMarked;

    private String sort = "time_desc";

    private Long userId;

    private List<Long> ids;

    public Integer getRoundRating() {
        return roundRating;
    }

    public void setRoundRating(Integer roundRating) {
        this.roundRating = roundRating;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {

        this.firstImg = firstImg;

    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getFloorPlanList() {
        return floorPlanList;
    }

    public void setFloorPlanList(List<String> floorPlanList) {
        this.floorPlanList = floorPlanList;
    }

    public List<MultipartFile> getHouseFiles() {
        return houseFiles;
    }

    public void setHouseFiles(List<MultipartFile> houseFiles) {
        this.houseFiles = houseFiles;
    }

    public List<MultipartFile> getFloorPlanFiles() {
        return floorPlanFiles;
    }

    public void setFloorPlanFiles(List<MultipartFile> floorPlanFiles) {
        this.floorPlanFiles = floorPlanFiles;
    }

    public Boolean getBookMarked() {
        return bookMarked;
    }

    public void setBookMarked(Boolean bookMarked) {
        this.bookMarked = bookMarked;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getId() {
        return this.id;
    }

    ;

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    ;

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return this.type;
    }

    ;

    public void setType(Integer type) {
        this.type = type;
        if (type == 1) {
            this.typeStr = "for sale";
        } else {
            this.typeStr = "for rent";
        }
    }

    public Long getPrice() {
        return this.price;
    }

    ;

    public void setPrice(Long price) {
        this.price = price;
        this.priceStr = price + "万";
    }

    public String getImages() {
        return this.images;
    }

    ;

    public void setImages(String images) {
        this.images = images;
        if (!Strings.isNullOrEmpty(images)) {
            List<String> imageList = Splitter.on(",").splitToList(images);
            if (imageList.size() > 0) {
                this.firstImg = imageList.get(0);
                this.imageList = imageList;
            }
        }
    }

    public Long getArea() {
        return this.area;
    }

    ;

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getBeds() {
        return this.beds;
    }

    ;

    public void setBeds(Long beds) {
        this.beds = beds;
    }

    public Long getBaths() {
        return this.baths;
    }

    ;

    public void setBaths(Long baths) {
        this.baths = baths;
    }

    public Double getRating() {
        return this.rating;
    }

    ;

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRemarks() {
        return this.remarks;
    }

    ;

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProperties() {
        return this.properties;
    }

    ;

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getFloorPlan() {
        return this.floorPlan;
    }

    ;

    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
    }

    public String getTags() {
        return this.tags;
    }

    ;

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    ;

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCityId() {
        return this.cityId;
    }

    ;

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCommunityId() {
        return this.communityId;
    }

    ;

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getAddress() {
        return this.address;
    }

    ;

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getState() {
        return this.state;
    }

    ;

    public void setState(Integer state) {
        this.state = state;
    }


}

