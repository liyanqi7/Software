package com.example.lyq.software.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;
import com.example.lyq.software.utils.SpUtils;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MyCollectionActivity extends ServerActivity {

    private ReleaseAdapter adapter;
    private TextView tvTitle;
    private TextView prompt;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        initView();
        initData();
        initServerData();
    }

    private void initData() {
        String type = getIntent().getStringExtra("type");
        tvTitle.setText(type);
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        prompt = (TextView) findViewById(R.id.prompt);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String state = "upload";
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ReleaseAdapter(releaseList,imageList,userList,state,MyCollectionActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public String getURL() {
        return Constants.BASE_URL + "/myCollectionServlet";
    }

    @Override
    public RequestBody getBody() {
        String userName = SpUtils.getTokenId(getBaseContext(),Constants.TOKENID);
        FormBody body = new FormBody.Builder()
                .add("userName", userName)
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
