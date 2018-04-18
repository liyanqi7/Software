package com.example.lyq.software.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Result;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.ui.bean.Volume;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;
import com.google.gson.Gson;

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
    private String userName;
    private ImageView ivFollow;
    private TextView tvFollow;
    private String followState;
    private LinearLayout llFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_process);
        userName = SpUtils.getTokenId(getBaseContext(), Constants.TOKENID);
        Log.e("user", "onCreate: " + userName);
        initView();
        initData();
        if (!(userName.isEmpty())){ //判断是否为空，不能用(userName == null)
            doJudgeFollow();//判断订单是否被收藏,初始化collectionState的值
        }
    }

    private void initData() {
        intent = getIntent();
        shop = (Shop) getIntent().getSerializableExtra("shopData");
        user = (Login) getIntent().getSerializableExtra("userData");
        volume = (Volume) getIntent().getSerializableExtra("volumeData");
        visitor = getIntent().getStringExtra("stateData");
        Glide.with(this).load(Constants.BASE_URL + user.getHead()).into(ivImage);
        Log.e("TAG", "initData: "+shop.getCompany() );
        tvCompany.setText(shop.getCompany());
        tvProvince.setText(shop.getProvince());
        tvCity.setText(shop.getCity());
        tvVolume.setText(volume.getSum() + "笔");
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
        llFollow = (LinearLayout) findViewById(R.id.ll_follow);
        llDelete = (LinearLayout) findViewById(R.id.ll_delete);
        llAgreen = (LinearLayout) findViewById(R.id.ll_agreen);
        ivImage = (ImageView) findViewById(R.id.iv_image);
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvProvince = (TextView) findViewById(R.id.tv_province);
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvVolume = (TextView) findViewById(R.id.tv_volume);
        ivFollow = (ImageView) findViewById(R.id.iv_follow);
        tvFollow = (TextView) findViewById(R.id.tv_follow);
        llHire = (LinearLayout) findViewById(R.id.ll_hire);
        llDelete.setOnClickListener(this);
        llAgreen.setOnClickListener(this);
        llFollow.setOnClickListener(this);
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
            case R.id.ll_follow:
                if (!userName.isEmpty()){
//                    doFollow();
                    if (followState.equals("true")){
                        doDeleteFollow();
                    } else {
                        doFollow();
                    }
                }else {
                    this.finish();
                    startActivity(new Intent(this,LoginActivity.class));
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                    Toast.makeText(this, "请先登录在收藏...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 添加关注
     */
    private void doFollow() {
        String url = Constants.BASE_URL + "/insertFollowServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("userName",userName)
                .add("shopName",shop.getUserName())
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Result rs = gson.fromJson(responseData, Result.class);
                if (rs.getResult().toString().equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            followState = "true";
                            ivFollow.setImageResource(R.mipmap.icon_flow_sel);
                            tvFollow.setText("取消关注");
                            tvFollow.setTextColor(getResources().getColor(R.color.text_orange));
                        }
                    });
                }
            }
        });
    }

    /**
     * 取消关注
     */
    private void doDeleteFollow() {
        String url = Constants.BASE_URL + "/deleteFollowServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("userName",userName)
                .add("shopName",shop.getUserName())
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
                        followState = "false";
                        ivFollow.setImageResource(R.mipmap.icon_flow_nor);
                        tvFollow.setText("关注");
                        tvFollow.setTextColor(getResources().getColor(R.color.text_gray));
                    }
                });
            }
        });
    }

    private void doJudgeFollow() {
        String url = Constants.BASE_URL + "/judgeFollowServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("userName",userName)
                .add("shopName",shop.getUserName())
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Result rs = gson.fromJson(responseData, Result.class);
                followState = rs.getResult().toString();
                if (followState.equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivFollow.setImageResource(R.mipmap.icon_flow_sel);
                            tvFollow.setText("已关注");
                            tvFollow.setTextColor(getResources().getColor(R.color.text_orange));
                        }
                    });
                }
            }
        });
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
