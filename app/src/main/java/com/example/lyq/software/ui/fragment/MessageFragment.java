package com.example.lyq.software.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.MessageAdapter;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Order;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.ui.bean.Volume;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageFragment extends Fragment {

    private List<Order> orderList = new ArrayList<Order>();
    private List<Shop> shopList = new ArrayList<Shop>();
    private List<Login> userList = new ArrayList<Login>();
    private List<Volume> volumeList = new ArrayList<Volume>();
    private MessageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Log.e("message", "onCreateView: "+orderList.size() );
        adapter = new MessageAdapter(orderList,shopList,userList,volumeList,this.getActivity());
        recyclerView.setAdapter(adapter);
        getApplyMessage();
        return view;
    }

    private void getApplyMessage() {
        String url = Constants.BASE_URL + "/applyTaskServlet";
        String uploadName = SpUtils.getTokenId(getContext(), Constants.TOKENID);
        RequestBody body = new FormBody.Builder()
                .add("uploadName",uploadName)
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
        JSONObject object = new JSONObject(responseData);
        JSONArray arrayOrder = object.getJSONArray("orderList");
        orderList.clear();
        Order order = null;
        for (int i = 0; i < arrayOrder.length(); i++) {
            order = new Order();
            JSONObject obj = arrayOrder.getJSONObject(i);
            order.setReleaseId(obj.getString("releaseId"));
            order.setUploadName(obj.getString("uploadName"));
            order.setApplyName(obj.getString("applyName"));
            order.setDescript(obj.getString("descript"));
            order.setDate(obj.getString("date"));
            order.setHead(obj.getString("head"));
            order.setBrowse(obj.getString("browse"));
            String applyName = order.getApplyName();
            orderList.add(order);
            getShopMessage(applyName);
        }
        /**
         * ERROR,当在此处获得ShopMessage时，只能获得一次ShopMessage的值，传递到Adapter中点击条目明显出错。
         * 但是，当只传递一个对象被adapter重复调用为什么也报错？？？？？？
         */
//        getShopMessage(applyName);
    }

    private void getShopMessage(String applyName) {
        String url = Constants.BASE_URL + "/applyShopServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("userName",applyName)
                .build();
        HttpUtil.post(url, body, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    parseSHOPWithGSON(responseData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseSHOPWithGSON(String responseData) throws JSONException {
        JSONObject object = new JSONObject(responseData);
        JSONArray shopArray = object.getJSONArray("shopList");
        JSONArray userArray = object.getJSONArray("userList");
        JSONArray volumeArray = object.getJSONArray("countList");
        Shop shop = null;
        Login user = null;
        Volume volume = null;
        Log.e("TAG", "parseJSONWithGSON: " + shopArray);
        Log.e("TAG", "parseJSONWithGSON: " + userArray);
        Log.e("TAG", "parseJSONWithGSON: " + volumeArray);
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
//        Log.e("TAG", "parseSHOPWithGSON: " + shop.getCompany() );
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
                adapter.notifyDataSetChanged();
            }
        });
    }
}
