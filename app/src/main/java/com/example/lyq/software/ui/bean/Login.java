package com.example.lyq.software.ui.bean;

/**
 * Created by lyq on 2018/1/5.
 */

public class Login {
    String result,tokenId;
    public Login(String result, String tokenId){
        this.result = result;
        this.tokenId = tokenId;
    }

    public String getResult() {
        return result;
    }

    public String getTokenId() {
        return tokenId;
    }
}
