package com.example.lyq.software.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;
import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Release;
import com.example.lyq.software.utils.HttpUtil;
import org.json.JSONArray;
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

public class AppActivity extends ServerActivity {

    private ReleaseAdapter adapter;
    private TextView prompt;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        initView();
        initServerData();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("APP开发");
        prompt = (TextView) findViewById(R.id.prompt);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String state = "upload";
        adapter = new ReleaseAdapter(releaseList,imageList,userList,state,AppActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public String getURL() {
        return Constants.BASE_URL + "/categoryServlet";
    }

    @Override
    public RequestBody getBody() {
        String type = "APP开发";
        FormBody body = new FormBody.Builder()
                .add("type", type)
                .add("state", "发布中")
                .add("level","first")
                .build();
        return body;
    }

    @Override
    public void updateUI() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void promptUI() {
        prompt.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

}
