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

public class MyCollectionActivity extends ServerActivity {

    private ReleaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        String type = getIntent().getStringExtra("type");
        TextView tvType = (TextView) findViewById(R.id.tv_type);
        tvType.setText(type);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String state = "upload";
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ReleaseAdapter(releaseList,imagesList,userList,state,MyCollectionActivity.this);
        recyclerView.setAdapter(adapter);
        initServerData();
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
}
