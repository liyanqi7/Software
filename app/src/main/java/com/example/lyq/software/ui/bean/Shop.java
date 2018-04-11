package com.example.lyq.software.ui.bean;

import java.io.Serializable;

/**
 * Created by lyq on 2018/4/9.
 */

public class Shop implements Serializable {

    String userName;
    String company;
    String province;
    String city;
    String nature;

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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}