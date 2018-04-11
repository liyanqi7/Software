package com.example.lyq.software.ui.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lyq on 2018/4/9.
 */

public class Volume implements Serializable {
    String sum;
    public Volume(){
        super();
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
