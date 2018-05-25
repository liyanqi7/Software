package com.example.lyq.software.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.lyq.software.R;
import com.example.lyq.software.ui.activity.MyFollowActivity;
import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Release;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.ui.bean.User;
import com.example.lyq.software.ui.bean.Volume;
import com.example.lyq.software.ui.ife.ShopService;
import com.example.lyq.software.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ServerShopActivity extends AppCompatActivity {

    private ShopService shopService;
    public List<Shop> shopList = new ArrayList<Shop>();
    public List<Login> userList = new ArrayList<Login>();
    public List<Volume> volumeList = new ArrayList<Volume>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_shop);
//        shopService = new MyFollowActivity();
    }

    public void initServerDate() {
        HttpUtil.post(shopService.getURL(), shopService.getBody(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                if (TextUtils.isEmpty(responseData)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shopService.promptUI();
                        }
                    });
                }
                try {
                    parseJSONToEntity(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseJSONToEntity(String responseData) throws Exception {
        shopList.clear();
        userList.clear();//!!!!!!!!! 注释:list不能写成list = null,这样将改变地址指向
        volumeList.clear();
        JSONObject object = new JSONObject(responseData);
        JSONArray shopArray = object.getJSONArray("shopList");
        JSONArray userArray = object.getJSONArray("userList");
        JSONArray volumeArray = object.getJSONArray("countList");
//        Log.e("TAG", "Follow: " + shopArray);
//        Log.e("TAG", "follow: " + userArray);
//        Log.e("TAG", "parseJSONWithGSON: " + volumeArray);
        Shop shop = null;
        Login user = null;
        Volume volume = null;
        for (int i = 0; i < shopArray.length(); i++) {
            shop = new Shop();
            JSONObject obj = shopArray.getJSONObject(i);
            shop.setUserName(obj.getString("userName"));
            shop.setCompany(obj.getString("company"));
            shop.setProvince(obj.getString("province"));
            shop.setCity(obj.getString("city"));
            shop.setNature(obj.getString("nature"));
            shopList.add(shop);
        }
        for (int i = 0; i < userArray.length(); i++) {
            user = new Login();
            JSONObject obj = userArray.getJSONObject(i);
            user.setHead(obj.getString("head"));
            userList.add(user);
        }
        for (int i = 0; i < volumeArray.length(); i++) {
            volume = new Volume();
            JSONObject obj = volumeArray.getJSONObject(i);
            volume.setSum(obj.getString("volume"));
            volumeList.add(volume);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                shopService.updateUI();
            }
        });
    }

}
