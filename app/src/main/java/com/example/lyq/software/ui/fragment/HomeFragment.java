package com.example.lyq.software.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.ui.activity.AppActivity;
import com.example.lyq.software.ui.activity.WeChatActivity;
import com.example.lyq.software.ui.activity.WebActivity;
import com.example.lyq.software.ui.adapter.HomeBannerAdapter;
import com.example.lyq.software.ui.custom.SuspendScrollView;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnTouchListener, View.OnClickListener, SuspendScrollView.OnScrollListener {

    private View view;
    private int[] imgs = new int[]{R.mipmap.banner_1,R.mipmap.banner_2,
            R.mipmap.banner_3,R.mipmap.banner_4};
    private ViewPager viewPager;
    private LinearLayout llIndicator;
    private int prePosition = 0;
    private int ORDER = 0;
    private int SHOP = 1;
    private LinearLayout llWeb;
    private LinearLayout llApp;
    private LinearLayout llWeChat;
    private int searchLayoutTop;
    private LinearLayout rlayout;
    private LinearLayout search01;
    private LinearLayout search02;
    private LinearLayout search_edit;
    private TextView tvOrder;
    private TextView tvShop;
    private View vOrder;
    private View vShop;
    private UploadFragment uploadFragment;
    private FragmentManager manager;
    private ShopFragment shopFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        initView();
        initEvents();//初始化事件
        initCarousel();
        initScroll();//悬浮ScrollView
        selectTab(ORDER);//默认选中第一个Tab
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("tag", "onStop: " );
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        //获取FragmentTransaction对象
//        FragmentTransaction transaction = manager.beginTransaction();
//        manager.beginTransaction().remove(uploadFragment);
    }

    private void initEvents() {
        tvOrder.setOnClickListener(this);
        tvShop.setOnClickListener(this);
        llWeb.setOnClickListener(this);
        llApp.setOnClickListener(this);
        llWeChat.setOnClickListener(this);
    }

    private void initView() {
        tvOrder = (TextView) view.findViewById(R.id.tv_order);
        tvShop = (TextView) view.findViewById(R.id.tv_shop);
        vOrder = view.findViewById(R.id.v_order);
        vShop = view.findViewById(R.id.v_shop);
        llWeb = (LinearLayout) view.findViewById(R.id.ll_web);
        llApp = (LinearLayout) view.findViewById(R.id.ll_app);
        llWeChat = (LinearLayout) view.findViewById(R.id.ll_weChat);
    }

    //实现悬浮效果的Scroll
    private void initScroll() {
        SuspendScrollView scrollView = (SuspendScrollView) view.findViewById(R.id.scrollView);
        search01 = (LinearLayout)view.findViewById(R.id.search01);
        search02 = (LinearLayout)view.findViewById(R.id.search02);
        search_edit = (LinearLayout) view.findViewById(R.id.search_edit);
        rlayout = (LinearLayout)view.findViewById(R.id.rlayout);
        scrollView.setOnScrollListener(this);
        //实现在fragment中获取searchLayout的顶部位置
        rlayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rlayout.getViewTreeObserver().removeOnPreDrawListener(this);
                searchLayoutTop = rlayout.getBottom();
                return false;
            }
        });
    }

    @Override
    public void onScroll(int scrollY) {
        if(scrollY >= searchLayoutTop){
            if (search_edit.getParent()!= search01) {
                search02.removeView(search_edit);
                search01.addView(search_edit);
            }
        }else {
            if (search_edit.getParent() != search02) {
                search01.removeView(search_edit);
                search02.addView(search_edit);
            }
        }
    }

    @Override
    public void onClick(View v) {
        resetNav();//将导航初始化(选择是订单或店铺)
        switch (v.getId()){
            case R.id.tv_order:
                selectTab(ORDER);
                break;
            case R.id.tv_shop:
                selectTab(SHOP);
                break;
            case R.id.ll_web:
                startActivity(new Intent(getContext(), WebActivity.class));
                break;
            case R.id.ll_app:
                startActivity(new Intent(getContext(), AppActivity.class));
                break;
            case R.id.ll_weChat:
                startActivity(new Intent(getContext(), WeChatActivity.class));
                break;
        }

    }

    private void resetNav() {
        tvOrder.setTextColor(getResources().getColor(R.color.text_gray_2));
        vOrder.setVisibility(View.GONE);
        tvShop.setTextColor(getResources().getColor(R.color.text_gray_2));
        vShop.setVisibility(View.GONE);
    }

    private void selectTab(int i) {
        //获取FragmentManager对象
//        manager = getActivity().getSupportFragmentManager();
        manager = getChildFragmentManager();//总结就是: getFragmentManager()是本级别管理者, getChildFragmentManager()是下一级别管理者.这实际上是一个树形管理结构.
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
        switch (i){
            case 0:
                Log.e("tag", "selectTab: "+i );
                tvOrder.setTextColor(getResources().getColor(R.color.text_orange));
                vOrder.setVisibility(View.VISIBLE);
                //如果对应的Fragment没有实例化，则进行实例化，并显示出来
                if (uploadFragment == null){
                    uploadFragment = new UploadFragment();
                    transaction.add(R.id.content, uploadFragment);
                } else {
                    transaction.show(uploadFragment);
                }
                break;
            case 1:
                Log.e("tag", "selectTab: "+i );
                tvShop.setTextColor(getResources().getColor(R.color.text_orange));
                vShop.setVisibility(View.VISIBLE);
                //如果对应的Fragment没有实例化，则进行实例化，并显示出来
                if (shopFragment == null){
                    shopFragment = new ShopFragment();
                    transaction.add(R.id.content, shopFragment);
                } else {
                    transaction.show(shopFragment);
                }
                break;
        }
        transaction.commit();//不要忘记提交事务
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (uploadFragment != null){
            transaction.hide(uploadFragment);
        }
        if (shopFragment != null){
            transaction.hide(shopFragment);
        }

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItemPosition = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItemPosition + 1);
            handler.sendEmptyMessageDelayed(1,2000);
        }
    };

    private void initCarousel() {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        llIndicator = (LinearLayout) view.findViewById(R.id.ll_indicator);
        viewPager.addOnPageChangeListener(this);
        int midPos = Integer.MAX_VALUE / 2;
        viewPager.setCurrentItem(midPos - midPos % imgs.length);
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(R.drawable.shape_indicator);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10,10);
            if (i != 0){
                layoutParams.leftMargin = 15;
                imageView.setEnabled(false);
            }
            imageView.setLayoutParams(layoutParams);
            llIndicator.addView(imageView);
        }
        //调用适配器,传递Context只能用getContext(),不能用参数Context context
        viewPager.setAdapter(new HomeBannerAdapter(imgs,getContext()));
        handler.sendEmptyMessageDelayed(1,2000);
        viewPager.setOnTouchListener(this);//设置触摸监听
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int pos = position % imgs.length;
        llIndicator.getChildAt(pos).setEnabled(true);
        llIndicator.getChildAt(prePosition).setEnabled(false);
        prePosition = pos;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:     //手指按下
                handler.removeCallbacksAndMessages(null);
                break;
            case MotionEvent.ACTION_MOVE:    //手指移动

                break;
            case MotionEvent.ACTION_UP:    //手指抬起
                handler.sendEmptyMessageDelayed(1,2000);    //发送空的延迟消息去轮播
                break;
        }
        return false;
    }
}
