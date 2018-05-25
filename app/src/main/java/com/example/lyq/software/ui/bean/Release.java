package com.example.lyq.software.ui.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lyq on 2018/3/12.
 */

public class Release implements Serializable{

    String releaseId;
    String userName;
    String descript;
    String type;
    String typeTwo;
    String price;
    String date;
    String beginTime;
    String endTime;

    public Release() {
        super();
    }

//    需要参数的构造函数
    public Release(String releaseId, String userName, String descript, String type, String typeTwo,
                   String price, String date, String beginTime, String endTime){
        super();
        this.releaseId = releaseId;
        this.userName = userName;
        this.descript = descript;
        this.type = type;
        this.typeTwo = typeTwo;
        this.price = price;
        this.date = date;
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

    public void setTypeTwo(String typeTwo) {
        this.typeTwo = typeTwo;
    }

    public String getTypeTwo() {
        return typeTwo;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
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

    public static Release setRelease(JSONObject json){
        try {
            return new Release(
                    json.getString("releaseId"),
                    json.getString("userName"),
                    json.getString("descript"),
                    json.getString("type"),
                    json.getString("typeTwo"),
                    json.getString("price"),
                    json.getString("date"),
                    json.getString("beginTime"),
                    json.getString("endTime")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
