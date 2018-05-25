package com.example.lyq.software.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.lyq.software.R;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class WeChatActivity extends ServerActivity {

    private ReleaseAdapter adapter;
    private String state = "upload";
    private TextView prompt;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        initView();
        initServerData();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("微信开发");
        prompt = (TextView) findViewById(R.id.prompt);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReleaseAdapter(releaseList,imageList,userList,state,WeChatActivity.this);
        recyclerView.setAdapter(adapter);
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
