package com.example.lyq.software.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.base.TokenSave;
import com.example.lyq.software.utils.SpUtils;

public class ChooseAddressActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvLocation;
    private TextView tvCountry;
    private TextView tvBeiJIng;
    private TextView tvChangChun;
    private TextView tvChengDu;
    private TextView tvNanJing;
    private TextView tvTitle;
    private TextView tvTianJing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        initView();
        initData();
        initEvents();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("切换城市");
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvCountry = (TextView) findViewById(R.id.tv_country);
        tvBeiJIng = (TextView) findViewById(R.id.tv_beiJing);
        tvChangChun = (TextView) findViewById(R.id.tv_changChun);
        tvChengDu = (TextView) findViewById(R.id.tv_chengDu);
        tvNanJing = (TextView) findViewById(R.id.tv_nanJing);
        tvTianJing = (TextView) findViewById(R.id.tv_tianJing);
    }

    private void initData() {
        if (!TextUtils.isEmpty(TokenSave.getInstance(getBaseContext()).getAddress()))
        {
            tvLocation.setText(TokenSave.getInstance(getBaseContext()).getAddress());
        }
    }

    private void initEvents() {
        tvCountry.setOnClickListener(this);
        tvBeiJIng.setOnClickListener(this);
        tvChangChun.setOnClickListener(this);
        tvChengDu.setOnClickListener(this);
        tvNanJing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_country:
                setToSave(tvCountry.getText().toString());
                break;
            case R.id.tv_beiJing:
                setToSave(tvBeiJIng.getText().toString());
                break;
            case R.id.tv_changChun:
                setToSave(tvChangChun.getText().toString());
                break;
            case R.id.tv_chengDu:
                setToSave(tvChengDu.getText().toString());
                break;
            case R.id.tv_nanJing:
                setToSave(tvNanJing.getText().toString());
                break;
            case R.id.tv_tianJing:
                setToSave(tvTianJing.getText().toString());
                break;
        }
    }

    private void setToSave(String address) {
        TokenSave.getInstance(getBaseContext()).saveAddress(address);
        finish();
    }

}
