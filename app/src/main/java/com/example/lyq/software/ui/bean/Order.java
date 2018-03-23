package com.example.lyq.software.ui.bean;

/**
 * Created by lyq on 2018/3/20.
 */

public class Order {

    private String releaseId;
    private String uploadName;
    private String applyName;
    private String head;
    private String descript;
    private String date;
    private String browse;

    public Order(){

    }

    public Order(String releaseId, String uploadName, String applyName, String descript, String date, String head, String browse){
        super();
        this.releaseId = releaseId;
        this.uploadName = uploadName;
        this.applyName = applyName;
        this.descript = descript;
        this.date = date;
        this.head = head;
        this.browse = browse;
    }

    public String getReleaseId(){
        return releaseId;
    }

    public void setReleaseId(String releaseId){
        this.releaseId = releaseId;
    }

    public String getUploadName(){
        return uploadName;
    }

    public void setUploadName(String uploadName){
        this.uploadName = uploadName;
    }

    public String getApplyName(){
        return applyName;
    }

    public void setApplyName(String applyName){
        this.applyName = applyName;
    }

    public String getHead(){
        return head;
    }

    public void setHead(String head){
        this.head = head;
    }

    public String getDescript(){
        return descript;
    }

    public void setDescript(String descript){
        this.descript = descript;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getBrowse(){
        return browse;
    }

    public void setBrowse(String browse){
        this.browse = browse;
    }
}
