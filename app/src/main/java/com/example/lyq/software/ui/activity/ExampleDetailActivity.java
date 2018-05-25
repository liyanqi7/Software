package com.example.lyq.software.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Example;

public class ExampleDetailActivity extends AppCompatActivity {

    private TextView tvDesgin;
    private TextView tvPrice;
    private TextView tvTheme;
    private TextView tvType;
    private TextView tvSystem;
    private TextView tvDesign2;
    private ImageView ivImage1;
    private ImageView ivImage2;
    private ImageView ivImage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_detail);
        initView();
        initData();
    }

    private void initView() {
        tvDesgin = (TextView) findViewById(R.id.tv_design);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvTheme = (TextView) findViewById(R.id.tv_theme);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvSystem = (TextView) findViewById(R.id.tv_system);
        tvDesign2 = (TextView) findViewById(R.id.tv_design2);
        ivImage1 = (ImageView) findViewById(R.id.iv_image1);
        ivImage2 = (ImageView) findViewById(R.id.iv_image2);
        ivImage3 = (ImageView) findViewById(R.id.iv_image3);
    }

    private void initData() {
        Example example = (Example) getIntent().getSerializableExtra("exampleData");
        tvDesgin.setText(example.getDesign());
        tvPrice.setText(example.getPrice());
        tvTheme.setText(example.getTheme());
        tvType.setText(example.getType());
        tvSystem.setText(example.getSystem());
        tvDesign2.setText(example.getDesign());
        Glide.with(this)
                .load(Constants.BASE_URL + example.getImage1())
                .into(ivImage1);
        Glide.with(this)
                .load(Constants.BASE_URL + example.getImage2())
                .into(ivImage2);
        Glide.with(this)
                .load(Constants.BASE_URL + example.getImage3())
                .into(ivImage3);
    }
}
