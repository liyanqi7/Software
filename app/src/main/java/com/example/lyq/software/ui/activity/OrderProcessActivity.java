package com.example.lyq.software.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderProcessActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llDelete;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_process);
        initView();
        intent = getIntent();
    }

    private void initData() {

    }

    private void initView() {
        llDelete = (LinearLayout) findViewById(R.id.ll_delete);
        llDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_delete:
                String applyName = intent.getStringExtra("applyName");
                String releaseId = intent.getStringExtra("releaseId");
                String url = Constants.BASE_URL + "/deleteApplyServlet";
                RequestBody body = new FormBody.Builder()
                        .add("applyName",applyName)
                        .add("releaseId",releaseId)
                        .build();
                HttpUtil.post(url, body, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                OrderProcessActivity.this.finish();
                                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                            }
                        });
                    }
                });
                break;
        }
    }
}
