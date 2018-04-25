package com.example.lyq.software.ui.bean;

import java.io.Serializable;

/**
 * Created by lyq on 2018/4/9.
 */

public class Shop implements Serializable {

    String userName;
    String company;
    String descript;
    String province;
    String city;
    String detail;
    String nature;
    String image1;
    String image2;

    public Shop(){
        super();
    }
//    public Shop(String userName,String company,String province,String city,String nature){
//        super();
//        this.userName = userName;
//        this.company = company;
//        this.province = province;
//        this.city = city;
//        this.nature = nature;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage2() {
        return image2;
    }
}