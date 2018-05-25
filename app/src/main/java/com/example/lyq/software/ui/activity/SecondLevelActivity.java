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

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SecondLevelActivity extends ServerActivity {

    private ReleaseAdapter adapter;
    private TextView prompt;
    private RecyclerView recyclerView;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_level);
        initView();
        initServerData();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        //state值不能再setAdapter之后获取，否则传入adapter的值为空
        String state = getIntent().getStringExtra("state");
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(title);
        prompt = (TextView) findViewById(R.id.prompt);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ReleaseAdapter(releaseList,imageList,userList,state,SecondLevelActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public String getURL() {
        return Constants.BASE_URL + "/categoryServlet";
    }

    @Override
    public RequestBody getBody() {
        FormBody body = new FormBody.Builder()
                .add("type", title)
                .add("state", "发布中")
                .add("level","second")
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
        prompt.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}
