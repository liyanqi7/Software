package com.example.lyq.software.ui.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lyq on 2018/5/11.
 */

public class User implements Serializable{

        String result,tokenId,nick,head;

        public User(){
            super();
        }

        public User(String nick, String head){
            this.nick = nick;
            this.head = head;
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

        public static User setUser(JSONObject json){
            try {
                return new User(
                        json.getString("nick"),
                        json.getString("head")
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
}
