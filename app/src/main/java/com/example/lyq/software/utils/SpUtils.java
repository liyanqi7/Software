package com.example.lyq.software.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lyq on 2017/12/20.
 */

public class SpUtils {

    private static final String spFileName = "app";

    public static Boolean getBoolean(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, true);
        return result;
    }

    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }

    public static void putTokenId(Context context, String tokenKey,
                                  String tokenData){
        SharedPreferences tokenPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tokenPreferences.edit();
        editor.putString(tokenKey, tokenData);
        editor.commit();
    }

    public static String getTokenId(Context context, String tokenKey){
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(tokenKey,"");
        return result;
    }

    public static void putNick(Context context, String tokenKey,
                               String tokenData){
        SharedPreferences tokenPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tokenPreferences.edit();
        editor.putString(tokenKey, tokenData);
        editor.commit();
    }

    public static String getNick(Context context, String tokenKey){
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(tokenKey,"");
        return result;
    }

    public static void putHead(Context context, String tokenKey,
                               String tokenData){
        SharedPreferences tokenPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tokenPreferences.edit();
        editor.putString(tokenKey, tokenData);
        editor.commit();
    }

    public static String getHead(Context context, String tokenKey){
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(tokenKey,"");
        return result;
    }

    public static void putShopName(Context context, String tokenKey,
                               String tokenData){
        SharedPreferences tokenPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tokenPreferences.edit();
        editor.putString(tokenKey, tokenData);
        editor.commit();
    }

    public static String getShopName(Context context, String tokenKey){
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(tokenKey,"");
        return result;
    }
}
