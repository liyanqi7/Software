package com.example.lyq.software.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.ui.bean.Volume;
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
    private LinearLayout llAgreen;
    private Shop shop;
    private Login user;
    private Volume volume;
    private ImageView ivImage;
    private TextView tvCompany;
    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvVolume;
    private String applyName;
    private String releaseId;
    private String visitor;
    private LinearLayout llHire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_process);
        initView();
        initData();
    }

    private void initData() {
        intent = getIntent();
        shop = (Shop) getIntent().getSerializableExtra("shopData");
        user = (Login) getIntent().getSerializableExtra("userData");
        volume = (Volume) getIntent().getSerializableExtra("volumeData");
        visitor = getIntent().getStringExtra("stateData");
//        Glide.with(this).load(Constants.BASE_URL + user.getHead()).into(ivImage);
//        Log.e("TAG", "initData: "+shop.getCompany() );
//        tvCompany.setText(shop.getCompany());
//        tvProvince.setText(shop.getProvince());
//        tvCity.setText(shop.getCity());
//        tvVolume.setText(volume.getSum() + "笔");
        if (visitor.equals("Browse")){
            llDelete.setVisibility(View.GONE);
            llAgreen.setVisibility(View.GONE);
            llHire.setVisibility(View.VISIBLE);
        }else {
            llDelete.setVisibility(View.VISIBLE);
            llAgreen.setVisibility(View.VISIBLE);
            llHire.setVisibility(View.GONE);
        }
    }

    private void initView() {
        llDelete = (LinearLayout) findViewById(R.id.ll_delete);
        llAgreen = (LinearLayout) findViewById(R.id.ll_agreen);
        ivImage = (ImageView) findViewById(R.id.iv_image);
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvProvince = (TextView) findViewById(R.id.tv_province);
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvVolume = (TextView) findViewById(R.id.tv_volume);
        llHire = (LinearLayout) findViewById(R.id.ll_hire);
        llDelete.setOnClickListener(this);
        llAgreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_delete:
                String state = "未通过";
                doChangeState(state);
                break;
            case R.id.ll_agreen:
                String agreenState = "已同意";
                doChangeState(agreenState);
                break;
        }
    }

    private void doChangeState(String state) {
        String url = Constants.BASE_URL + "/changeApplyState";
        applyName = intent.getStringExtra("applyName");
        releaseId = intent.getStringExtra("releaseId");
        RequestBody body = new FormBody.Builder()
                .add("applyName",applyName)
                .add("releaseId",releaseId)
                .add("state",state)
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
    }
}
