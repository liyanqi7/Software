package com.example.lyq.software.ui.bean;

/**
 * Created by lyq on 2018/3/12.
 */

public class Release {

    String releaseId;
    String userName;
    String descript;
    String type;
    String price;
    String beginTime;
    String endTime;

    public Release() {
        super();
    }

    //需要参数的构造函数
    public Release(String releaseId, String userName, String descript, String type, String price, String beginTime, String endTime){
        super();
        this.releaseId = releaseId;
        this.userName = userName;
        this.descript = descript;
        this.type = type;
        this.price = price;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public void setReleaseId(String releaseId){
        this.releaseId = releaseId;
    }

    public String getReleaseId(){
        return releaseId;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setDescript(String descript){
        this.descript = descript;
    }

    public String getDescript(){
        return descript;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setBeginTime(String beginTime){
        this.beginTime = beginTime;
    }

    public String getBeginTime(){
        return beginTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public String getEndTime(){
        return endTime;
    }
}
