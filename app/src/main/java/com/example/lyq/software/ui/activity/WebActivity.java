package com.example.lyq.software.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;

public class WebActivity extends ServerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                Log.e("result", "initServerData: "+releaseList.size() );
//            }
//        }).start();
        initServerData("网站建设");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (releaseList != null){
            ReleaseAdapter adapter = new ReleaseAdapter(releaseList,imagesList,userList,WebActivity.this);
            recyclerView.setAdapter(adapter);
        }
    }
}
