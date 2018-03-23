package com.example.lyq.software.ui.bean;

import java.io.Serializable;

/**
 * Created by lyq on 2018/1/5.
 */

public class Login implements Serializable{
    String result,tokenId,nick,head;

    public Login(){
        super();
    }

    public Login(String result, String tokenId, String nick, String head){
        this.result = result;
        this.tokenId = tokenId;
        this.nick = nick;
        this.head = head;
    }

    public String getResult() {
        return result;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
