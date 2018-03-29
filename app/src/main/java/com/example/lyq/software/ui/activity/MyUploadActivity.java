package com.example.lyq.software.ui.activity;

import android.content.Intent;
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

public class MyUploadActivity extends ServerActivity {

    private ReleaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upload);
        String type = getIntent().getStringExtra("type");
        TextView tvType = (TextView) findViewById(R.id.tv_type);
        tvType.setText(type);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ReleaseAdapter(releaseList,imagesList,userList,MyUploadActivity.this);
        recyclerView.setAdapter(adapter);
        initServerData(SpUtils.getTokenId(getBaseContext(),Constants.TOKENID));
    }

    @Override
    public String getURL() {
        return Constants.BASE_URL + "/myUploadServlet";
    }

    @Override
    public void updateUI() {
        adapter.notifyDataSetChanged();
    }
}
