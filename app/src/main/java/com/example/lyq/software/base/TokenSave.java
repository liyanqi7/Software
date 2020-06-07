package com.example.lyq.software.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lyq on 2018/5/10.
 */

public class TokenSave {

    private static TokenSave mTokenSave;
    private final SharedPreferences mPrefs;
    private final SharedPreferences.Editor editor;
    public static final String ADDRESS = "address";
    private String mAddress;

    /**
     * 类名+方法名调用的方法叫做静态方法,属于类级别方法,不希望类的构造方法是public的,也就是用户无法直接new出来的,通常就会通过这么一个静态方法来调用获取实例
     * 一般提倡通过类名来使用，因为静态方法只要定义了类，不必建立类的实例就可使用
     * @param context
     * @return
     */
    public static TokenSave getInstance(Context context){
        if (mTokenSave == null){
            mTokenSave = new TokenSave(context.getApplicationContext());
        }
        return mTokenSave;
    }

    private TokenSave(Context context){
        mPrefs = context.getSharedPreferences("address", Context.MODE_PRIVATE);
        editor = mPrefs.edit();
        editor.apply();
    }

    public void saveAddress(String address){
        mAddress = address;
        editor.putString(ADDRESS,address);
        editor.apply();
    }

    public String getAddress(){
        if (mAddress != null) {
            //存储时saveAddress()给mAdress赋过初值
            return mAddress;
        }
        //不经过存储，直接从本地调用
        mAddress = mPrefs.getString(ADDRESS,null);
        return mAddress;
    }
}

