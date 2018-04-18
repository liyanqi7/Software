package com.example.lyq.software.ui.activity;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.example.lyq.software.R;
import com.example.lyq.software.ui.fragment.AddRequirementsFragment;
import com.example.lyq.software.ui.fragment.HomeFragment;
import com.example.lyq.software.ui.fragment.MessageFragment;
import com.example.lyq.software.ui.fragment.MyFragment;
import com.example.lyq.software.ui.fragment.NearByFragment;
import com.example.lyq.software.ui.fragment.ShopFragment;

public class MainActivity extends FragmentActivity {

    private Class mFragmentArray[] = {HomeFragment.class, NearByFragment.class,
            AddRequirementsFragment.class, MessageFragment.class, MyFragment.class};
    private  int mImageViewArray[] = {R.drawable.tab_menu_home,R.drawable.tab_menu_nearby,
            R.drawable.tab_menu_add,R.drawable.tab_menu_message,R.drawable.tab_menu_my};
    private String mTextViewArray[] = {"首页","附近","添加","消息","我的"};
    private LayoutInflater layoutInflater;
    private FragmentTabHost mTabHost;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initTabHost();
    }

    private void initTabHost() {
        layoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        frameLayout = (FrameLayout) findViewById(R.id.realtabcontent);
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec,mFragmentArray[i],null);
            updateText(mTabHost);
            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    updateText(mTabHost);
                }
            });
        }
    }

    private void updateText(FragmentTabHost mTabHost) {
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            if (i == 2){
                mTabHost.setVisibility(View.GONE);
            }else {
                mTabHost.setVisibility(View.VISIBLE);
            }
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.textview);
            tv.setTextSize(10);
            if (mTabHost.getCurrentTab() == i){
                tv.setTextColor(this.getResources().getColor(R.color.back_orange_1));
            }else {
                tv.setTextColor(this.getResources().getColor(R.color.text_gray));
            }
        }
    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.item_tab_view,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextViewArray[index]);
        return view;
    }
}
