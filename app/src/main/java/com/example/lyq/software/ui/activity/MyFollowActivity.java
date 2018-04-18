package com.example.lyq.software.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ShopAdapter;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.ui.bean.Volume;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyFollowActivity extends AppCompatActivity {

    public List<Shop> shopList = new ArrayList<Shop>();
    public List<Login> userList = new ArrayList<Login>();
    public List<Volume> volumeList = new ArrayList<Volume>();
    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);
        String type = getIntent().getStringExtra("type");
        TextView tvType = (TextView) findViewById(R.id.tv_type);
        tvType.setText(type);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Log.e("TAG", "onResponse: " + "3333");
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ShopAdapter(shopList,userList,volumeList,this);
        recyclerView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        String url = Constants.BASE_URL + "/myFollowServlet";
        String userName = SpUtils.getTokenId(this,Constants.TOKENID);
        RequestBody body = new FormBody.Builder()
                .add("userName", userName)
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    parseJSONWithGSON(responseData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseJSONWithGSON(String responseData) throws JSONException {
        shopList.clear();
        userList.clear();//!!!!!!!!! 注释:list不能写成list = null,这样将改变地址指向
        volumeList.clear();
        JSONObject object = new JSONObject(responseData);
        JSONArray shopArray = object.getJSONArray("shopList");
        JSONArray userArray = object.getJSONArray("userList");
        JSONArray volumeArray = object.getJSONArray("countList");
        Log.e("TAG", "Follow: " + shopArray);
        Log.e("TAG", "foloww: " + userArray);
        Log.e("TAG", "parseJSONWithGSON: " + volumeArray);
//        shopList.addAll((Collection<? extends Shop>) shopArray);
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
                adapter.notifyDataSetChanged();
            }
        });
    }
}
