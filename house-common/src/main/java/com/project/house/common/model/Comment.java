package com.project.house.common.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by user on 2018-05-27.
 */
public class Comment {


    private  Long id;

    //评论内容
    @NotNull
    private  String content;

    //房屋id
    private  Long houseId;

    //发布时间戳
    private Date createTime;

    //博客id
    private  Long blogId;

    //类型1-房产评论，2-博客评论
    private  Integer type;

    //评论用户
    private  Long userId;


    public  Long  getId(){
        return  this.id;
    };
    public  void  setId(Long id){
        this.id=id;
    }

    public  String  getContent(){
        return  this.content;
    };
    public  void  setContent(String content){
        this.content=content;
    }

    public  Long  getHouseId(){
        return  this.houseId;
    };
    public  void  setHouseId(Long houseId){
        this.houseId=houseId;
    }

    public  Date  getCreateTime(){
        return  this.createTime;
    };
    public  void  setCreateTime(Date createTime){
        this.createTime=createTime;
    }

    public  Long  getBlogId(){
        return  this.blogId;
    };
    public  void  setBlogId(Long blogId){
        this.blogId=blogId;
    }

    public  Integer  getType(){
        return  this.type;
    };
    public  void  setType(Integer type){
        this.type=type;
    }

    public  Long  getUserId(){
        return  this.userId;
    };
    public  void  setUserId(Long userId){
        this.userId=userId;
    }
}
