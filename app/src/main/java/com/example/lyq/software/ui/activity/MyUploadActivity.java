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
import com.example.lyq.software.utils.SpUtils;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MyUploadActivity extends ServerActivity {

    private ReleaseAdapter adapter;
    private String state;
    private TextView tvTitle;
    private TextView prompt;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upload);
        initView();
        String type = getIntent().getStringExtra("type");
        tvTitle.setText(type);
        initServerData();
    }

    private void initView() {
        //state值不能再setAdapter之后获取，否则传入adapter的值为空
        state = getIntent().getStringExtra("state");
        tvTitle = (TextView) findViewById(R.id.tv_title);
        prompt = (TextView) findViewById(R.id.prompt);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ReleaseAdapter(releaseList,imageList,userList,state,MyUploadActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public String getURL() {
        return Constants.BASE_URL + "/myUploadServlet";
    }

    @Override
    public RequestBody getBody() {
        String userName = SpUtils.getTokenId(getBaseContext(),Constants.TOKENID);
        FormBody body = new FormBody.Builder()
                .add("userName", userName)
                .add("state",state)
                .build();
        return body;
    }

    @Override
    public void updateUI() {
        //更新数据，只更新获取过来的
        adapter.notifyDataSetChanged();
    }

    @Override
    public void promptUI() {
        prompt.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}
