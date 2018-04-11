package com.example.lyq.software.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class WeChatActivity extends ServerActivity {

    private ReleaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String state = "upload";
        adapter = new ReleaseAdapter(releaseList,imagesList,userList,state,WeChatActivity.this);
        recyclerView.setAdapter(adapter);
        initServerData();
    }

    public String getURL() {
        return Constants.BASE_URL + "/categoryServlet";
    }

    @Override
    public RequestBody getBody() {
        String type = "微信开发";
        FormBody body = new FormBody.Builder()
                .add("type", type)
                .add("state", "发布中")
                .build();
        return body;
    }

    @Override
    public void updateUI() {
        adapter.notifyDataSetChanged();
    }
}
