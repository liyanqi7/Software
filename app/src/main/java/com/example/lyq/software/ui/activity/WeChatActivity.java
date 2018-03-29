package com.example.lyq.software.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;

public class WeChatActivity extends ServerActivity {

    private ReleaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReleaseAdapter(releaseList,imagesList,userList,WeChatActivity.this);
        recyclerView.setAdapter(adapter);
        initServerData("微信开发");
    }

    public String getURL() {
        return Constants.BASE_URL + "/categoryServlet";
    }

    @Override
    public void updateUI() {
        adapter.notifyDataSetChanged();
    }
}
