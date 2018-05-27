package com.project.house.common.model;

import java.util.Date;

/**
 * Created by user on 2018-05-27.
 */
public class UserMsg {


    private  Long id;

    //消息
    private  String msg;

    //创建时间
    private  Date createTime;

    //经纪人id
    private  Long agentId;

    //房屋id
    private  Long houseId;

    //用户姓名
    private  String userName;

    //留言者Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  Long  getId(){
        return  this.id;
    };
    public  void  setId(Long id){
        this.id=id;
    }

    public  String  getMsg(){
        return  this.msg;
    };
    public  void  setMsg(String msg){
        this.msg=msg;
    }

    public Date getCreateTime(){
        return  this.createTime;
    };
    public  void  setCreateTime(Date createTime){
        this.createTime=createTime;
    }

    public  Long  getAgentId(){
        return  this.agentId;
    };
    public  void  setAgentId(Long agentId){
        this.agentId=agentId;
    }

    public  Long  getHouseId(){
        return  this.houseId;
    };
    public  void  setHouseId(Long houseId){
        this.houseId=houseId;
    }

    public  String  getUserName(){
        return  this.userName;
    };
    public  void  setUserName(String userName){
        this.userName=userName;
    }

}
