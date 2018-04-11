package com.example.lyq.software.ui.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class WebActivity extends ServerActivity {

    private ReleaseAdapter adapter;
    private SwipeRefreshLayout swipRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
//        initServerData("网站建设");
//        swipRefresh = (SwipeRefreshLayout) findViewById(R.id.swipRefresh);
//        swipRefresh.setColorSchemeResources(R.color.colorPrimary);
//        swipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initServerData();
////                refreshApply();
//            }
//        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //使用adapter先初始化界面，再传值进adapter
        String state = "upload";
        adapter = new ReleaseAdapter(releaseList,imagesList,userList,state,WebActivity.this);
        recyclerView.setAdapter(adapter);
        initServerData();//如果先初始化数据，启动子线程进行网络请求。继续执行adapter时，数据可能尚未请求完成，传入adapter的数据可能为空
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
}
