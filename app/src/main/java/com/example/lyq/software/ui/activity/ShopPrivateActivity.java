package com.example.lyq.software.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;
import com.example.lyq.software.utils.SpUtils;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ShopPrivateActivity extends ServerActivity {

    private ReleaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_private);
        initView();
        initServerData();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("我的专案");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //state值判断处理订单的界面
        String state = "upload";
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ReleaseAdapter(releaseList,imageList,userList,state,ShopPrivateActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public String getURL() {
        return Constants.BASE_URL + "/authorityReleaseServlet";
    }

    @Override
    public RequestBody getBody() {
        String authority = SpUtils.getTokenId(getApplicationContext(),Constants.TOKENID);
        FormBody body = new FormBody.Builder()
                .add("authority", authority)
                .build();
        return body;
    }

    /**
     * 继承父类抽象方法的实现
     */
    @Override
    public void updateUI() {
        //notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
        adapter.notifyDataSetChanged();
    }

    @Override
    public void promptUI() {

    }
}
