package com.project.house.common.model;

public class Agency {


    private  Long id;

    //经纪机构名称
    private  String name;

    //地址
    private  String address;

    //手机
    private  String phone;

    //电子邮件
    private  String email;

    //描述
    private  String aboutUs;

    //电话
    private  String mobile;

    //网站
    private  String webSite;


    public  Long  getId(){
        return  this.id;
    };
    public  void  setId(Long id){
        this.id=id;
    }

    public  String  getName(){
        return  this.name;
    };
    public  void  setName(String name){
        this.name=name;
    }

    public  String  getAddress(){
        return  this.address;
    };
    public  void  setAddress(String address){
        this.address=address;
    }

    public  String  getPhone(){
        return  this.phone;
    };
    public  void  setPhone(String phone){
        this.phone=phone;
    }

    public  String  getEmail(){
        return  this.email;
    };
    public  void  setEmail(String email){
        this.email=email;
    }

    public  String  getAboutUs(){
        return  this.aboutUs;
    };
    public  void  setAboutUs(String aboutUs){
        this.aboutUs=aboutUs;
    }

    public  String  getMobile(){
        return  this.mobile;
    };
    public  void  setMobile(String mobile){
        this.mobile=mobile;
    }

    public  String  getWebSite(){
        return  this.webSite;
    };
    public  void  setWebSite(String webSite){
        this.webSite=webSite;
    }
}
