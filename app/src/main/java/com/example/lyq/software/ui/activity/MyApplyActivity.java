package com.example.lyq.software.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.MyApplyAdapter;
import com.example.lyq.software.ui.bean.Order;
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

public class MyApplyActivity extends BaseActivity {

    private List<Order> orderList = new ArrayList<Order>();;
    private MyApplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply);
        String type = getIntent().getStringExtra("type");
        TextView tvType = (TextView) findViewById(R.id.tv_type);
        tvType.setText(type);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyApplyAdapter(orderList);
        recyclerView.setAdapter(adapter);
        initData();
    }
    private void initData() {
        String url = Constants.BASE_URL + "/myApplyServlet";
        String applyName = SpUtils.getTokenId(this, Constants.TOKENID);
        RequestBody body = new FormBody.Builder()
                .add("applyName",applyName)
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
//        orderList.addAll((ArrayList<Order>)arrayOrder);
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
            order.setState(obj.getString("state"));
            orderList.add(order);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
