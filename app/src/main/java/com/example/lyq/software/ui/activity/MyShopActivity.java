package com.example.lyq.software.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.fragment.MyApplyFragment;
import com.example.lyq.software.ui.fragment.ShopHomeFragment;
import com.example.lyq.software.utils.SpUtils;

public class MyShopActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvHomePage;
    private TextView tvOrder;
    private TextView tvDeal;
    private TextView tvData;
    private MyApplyFragment mHomePageFragment;
    private ImageView ivImage;
    int HOMEPAGE = 0;
    private int ORDER = 1;
    private int DEAL = 2;
    private int DATA = 3;
    private View vHomePage;
    private View vOrder;
    private View vDeal;
    private View vData;
    private ShopHomeFragment mShopHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        initView();
        initEvents();
        selectTab(HOMEPAGE);//初始化fragment
    }

    private void initEvents() {
        tvHomePage.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        tvDeal.setOnClickListener(this);
        tvData.setOnClickListener(this);
    }

    private void initView() {
        ivImage = (ImageView) findViewById(R.id.iv_image);
        Glide.with(this)
                .load(Constants.BASE_URL + SpUtils.getHead(getBaseContext(),Constants.HEAD))
                .into(ivImage);
        //四个指示器控件
        tvHomePage = (TextView) findViewById(R.id.tv_homePage);
        tvOrder = (TextView) findViewById(R.id.tv_order);
        tvDeal = (TextView) findViewById(R.id.tv_deal);
        tvData = (TextView) findViewById(R.id.tv_data);
        vHomePage = findViewById(R.id.v_homePage);
        vOrder = findViewById(R.id.v_order);
        vDeal = findViewById(R.id.v_deal);
        vData = findViewById(R.id.v_data);
    }

    @Override
    public void onClick(View v) {
        resetTab();//初始化字体颜色
        switch (v.getId()){
            case R.id.tv_homePage:
                selectTab(HOMEPAGE);
                break;
            case R.id.tv_order:
                selectTab(ORDER);
                break;
            case R.id.tv_deal:
                selectTab(DEAL);
                break;
            case R.id.tv_data:
                selectTab(DATA);
                break;
        }
    }

    private void selectTab(int i) {
        //获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的fragment
        hideFragments(transaction);
        switch (i) {
            case 0:
                tvHomePage.setTextColor(this.getResources().getColor(R.color.text_orange));
                vHomePage.setBackgroundColor(this.getResources().getColor(R.color.text_orange));
                //如果对应的Fragment没有实例化，则进行实例化，并显示出来
                if (mShopHomeFragment == null) {
                    mShopHomeFragment = new ShopHomeFragment();
                    transaction.add(R.id.content, mShopHomeFragment);
                } else {
                    //如果对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mShopHomeFragment);
                }
                break;
            case 1:
                tvOrder.setTextColor(this.getResources().getColor(R.color.text_orange));
                vOrder.setBackgroundColor(this.getResources().getColor(R.color.text_orange));
                if (mHomePageFragment == null) {
                    mHomePageFragment = new MyApplyFragment();
                    transaction.add(R.id.content, mHomePageFragment);
                } else {
                    transaction.show(mHomePageFragment);
                }
                break;
            case 2:
                tvDeal.setTextColor(this.getResources().getColor(R.color.text_orange));
                vDeal.setBackgroundColor(this.getResources().getColor(R.color.text_orange));
                if (mHomePageFragment == null) {
                    mHomePageFragment = new MyApplyFragment();
                    transaction.add(R.id.content, mHomePageFragment);
                } else {
                    transaction.show(mHomePageFragment);
                }
                break;
            case 3:
                tvData.setTextColor(this.getResources().getColor(R.color.text_orange));
                vData.setBackgroundColor(this.getResources().getColor(R.color.text_orange));
                if (mHomePageFragment == null) {
                    mHomePageFragment = new MyApplyFragment();
                    transaction.add(R.id.content, mHomePageFragment);
                } else {
                    transaction.show(mHomePageFragment);
                }
                break;
        }
        //不要忘记提交事务
        transaction.commit();
        
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mShopHomeFragment!= null) {
            transaction.hide(mShopHomeFragment);
        }
        if (mHomePageFragment != null) {
            transaction.hide(mHomePageFragment);
        }
        if (mHomePageFragment != null) {
            transaction.hide(mHomePageFragment);
        }
        if (mHomePageFragment != null) {
            transaction.hide(mHomePageFragment);
        }
    }

    private void resetTab() {
        tvHomePage.setTextColor(this.getResources().getColor(R.color.text_gray));
        tvOrder.setTextColor(this.getResources().getColor(R.color.text_gray));
        tvDeal.setTextColor(this.getResources().getColor(R.color.text_gray));
        tvData.setTextColor(this.getResources().getColor(R.color.text_gray));
        vHomePage.setBackgroundColor(this.getResources().getColor(R.color.back_gray));
        vOrder.setBackgroundColor(this.getResources().getColor(R.color.back_gray));
        vDeal.setBackgroundColor(this.getResources().getColor(R.color.back_gray));
        vData.setBackgroundColor(this.getResources().getColor(R.color.back_gray));
    }
}
