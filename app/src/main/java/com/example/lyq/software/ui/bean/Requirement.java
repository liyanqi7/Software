package com.example.lyq.software.ui.bean;

/**
 * Created by lyq on 2018/1/19.
 */

public class Requirement {
    private String type;
    private String content;
    private int img;

    public Requirement(String type, String content, int img){
        this.type = type;
        this.content = content;
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getImg() {
        return img;
    }
}
