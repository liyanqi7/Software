package com.example.lyq.software.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.TokenSave;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.ChooseAddressActivity;
import com.example.lyq.software.ui.adapter.ShopAdapter;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.ui.bean.Volume;
import com.example.lyq.software.utils.HttpUtil;

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

public class NearByFragment extends Fragment {

    private View view;
    public List<Shop> shopList = new ArrayList<Shop>();
    public List<Login> userList = new ArrayList<Login>();
    public List<Volume> volumeList = new ArrayList<Volume>();
    private ShopAdapter adapter;
    private TextView tvCount;
    private RecyclerView recyclerView;
    private TextView tvChange;
    private TextView tvCity;
    private LinearLayoutManager layoutManager;
    private String location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_near_by, container, false);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        location = TokenSave.getInstance(getContext()).getAddress();
        if (location != null){
            tvCity.setText(location);
        }
        /**
         * 从服务端获取店铺数据
         */
        getShopData();
    }

    private void initView() {
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        tvChange = (TextView) view.findViewById(R.id.tv_change);
        tvCity = (TextView) view.findViewById(R.id.tv_city);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ShopAdapter(shopList,userList,volumeList,getActivity());//使用adapter先初始化界面，再传值进adapter
        recyclerView.setAdapter(adapter);
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChooseAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getShopData() {
        String url = Constants.BASE_URL + "/nearbyShopServlet";
        String city = TokenSave.getInstance(getContext()).getAddress();
        RequestBody body = new FormBody.Builder()
                .add("city", location)
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
        final JSONArray shopArray = object.getJSONArray("shopList");
        JSONArray userArray = object.getJSONArray("userList");
        JSONArray volumeArray = object.getJSONArray("countList");
        Log.e("TAG", "parseJSONWithGSON: " + shopArray);
        Log.e("TAG", "parseJSONWithGSON: " + userArray);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCount.setText(String.valueOf(shopList.size()));//注意：setText()中不能是int型数据
                adapter.notifyDataSetChanged();
            }
        });
    }

}
