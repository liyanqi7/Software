package com.example.lyq.software.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.lyq.software.R;
import com.example.lyq.software.ui.activity.AppActivity;
import com.example.lyq.software.ui.activity.WeChatActivity;
import com.example.lyq.software.ui.activity.WebActivity;
import com.example.lyq.software.ui.adapter.HomeBannerAdapter;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnTouchListener, View.OnClickListener {

    private View view;
    Context context;
    private int[] imgs = new int[]{R.mipmap.banner_1,R.mipmap.banner_2,
            R.mipmap.banner_3,R.mipmap.banner_4};
    private ViewPager viewPager;
    private LinearLayout llIndicator;
    private int prePosition = 0;
    private LinearLayout llWeb;
    private LinearLayout llApp;
    private LinearLayout llWeChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        initView();
        initCarousel();
        return view;
    }

    private void initView() {
//        Intent intent = new Intent(getContext(),);
        llWeb = (LinearLayout) view.findViewById(R.id.ll_web);
        llApp = (LinearLayout) view.findViewById(R.id.ll_app);
        llWeChat = (LinearLayout) view.findViewById(R.id.ll_weChat);
        llWeb.setOnClickListener(this);
        llApp.setOnClickListener(this);
        llWeChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
