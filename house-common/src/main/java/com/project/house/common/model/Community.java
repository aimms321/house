package com.project.house.common.model;

public class Community {

    //主键
    private  Long id;

    //城市编码
    private  String cityCode;

    //城市名称
    private  String cityName;

    //小区名称
    private  String name;


    public  Long  getId(){
        return  this.id;
    };
    public  void  setId(Long id){
        this.id=id;
    }

    public  String  getCityCode(){
        return  this.cityCode;
    };
    public  void  setCityCode(String cityCode){
        this.cityCode=cityCode;
    }

    public  String  getCityName(){
        return  this.cityName;
    };
    public  void  setCityName(String cityName){
        this.cityName=cityName;
    }

    public  String  getName(){
        return  this.name;
    };
    public  void  setName(String name){
        this.name=name;
    }


}
