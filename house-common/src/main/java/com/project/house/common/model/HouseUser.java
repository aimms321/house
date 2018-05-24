package com.project.house.common.model;

import java.util.Date;

public class HouseUser {


    private  Long id;

    //房屋id
    private  Long houseId;

    //用户id
    private  Long userId;

    //创建时间
    private Date createTime;

    //1-售卖，2-收藏
    private  Integer type;


    public  Long  getId(){
        return  this.id;
    };
    public  void  setId(Long id){
        this.id=id;
    }

    public  Long  getHouseId(){
        return  this.houseId;
    };
    public  void  setHouseId(Long houseId){
        this.houseId=houseId;
    }

    public  Long  getUserId(){
        return  this.userId;
    };
    public  void  setUserId(Long userId){
        this.userId=userId;
    }

    public  Date  getCreateTime(){
        return  this.createTime;
    };
    public  void  setCreateTime(Date createTime){
        this.createTime=createTime;
    }

    public  Integer  getType(){
        return  this.type;
    };
    public  void  setType(Integer type){
        this.type=type;
    }
}
