package com.example.lyq.software.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class WebActivity extends ServerActivity implements View.OnClickListener {

    private ReleaseAdapter adapter;
    private SwipeRefreshLayout swipRefresh;
    private TextView tvWebsite;
    private TextView tvBuild;
    private TextView tvMobile;
    private TextView tvUi;
    private TextView prompt;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        // 如果先初始化数据，启动子线程进行网络请求。继续执行adapter时，数据可能尚未请求完成，传入adapter的数据可能为空
        initServerData();
        initEvents();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("网站建设");
        prompt = (TextView) findViewById(R.id.prompt);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvWebsite = (TextView) findViewById(R.id.tv_website);
        tvBuild = (TextView) findViewById(R.id.tv_build);
        tvMobile = (TextView) findViewById(R.id.tv_mobile);
        tvUi = (TextView) findViewById(R.id.tv_ui);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //使用adapter先初始化界面，再传值进adapter
        String state = "upload";
        adapter = new ReleaseAdapter(releaseList,imageList,userList,state,WebActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void initEvents() {
        tvWebsite.setOnClickListener(this);
        tvBuild.setOnClickListener(this);
        tvMobile.setOnClickListener(this);
        tvUi.setOnClickListener(this);
    }

    public String getURL() {
        return Constants.BASE_URL + "/categoryServlet";
    }

    @Override
    public RequestBody getBody() {
        String type = "网站建设";
        FormBody body = new FormBody.Builder()
                .add("type", type)
                .add("state", "发布中")
                .add("level","first")
                .build();
        return body;
    }

    /**
     * 继承父类抽象方法的实现
     */
    @Override
    public void updateUI() {
        adapter.notifyDataSetChanged();//notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
//        swipRefresh.setRefreshing(false);
    }

    @Override
    public void promptUI() {
        prompt.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_website:
                startAction("网站定制");
                break;
            case R.id.tv_build:
                startAction("模板建站");
                break;
            case R.id.tv_mobile:
                startAction("手机网站");
                break;
            case R.id.tv_ui:
                startAction("前端开发");
                break;
        }
    }

    private void startAction(String title) {
        String state = "发布中";
        Intent intent = new Intent();
        intent.setClass(this,SecondLevelActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("state",state);
        startActivity(intent);
    }
}
