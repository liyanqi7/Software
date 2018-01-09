package com.example.lyq.software.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.GuideViewPagerAdapter;
import com.example.lyq.software.utils.SpUtils;
import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends Activity implements OnClickListener {

    //引导页图片资源
    private static final int[] pics = {R.layout.guide_view1,R.layout.guide_view2,
            R.layout.guide_view3,R.layout.guide_view4};
    private List<View> views;
    private Button startBtn;
    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private ImageView[] dots;//底部小点图片
    private int currentIndex;// 记录当前选中位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_guide);
        views = new ArrayList<View>();
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i],null);
            if (i == pics.length - 1){
                startBtn = (Button) view.findViewById(R.id.btn_login);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }
            views.add(view);
        }
        vp = (ViewPager) findViewById(R.id.vp_guide);
        adapter = new GuideViewPagerAdapter(views);//初始化adapter
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new PageChangeListener());
        initDots();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //如果切换到后台，就设置下次不进入功能引导页
        SpUtils.putBoolean(WelcomeGuideActivity.this, Constants.FIRST_OPEN,false);
        finish();
    }

    /**
     * 初始化指示器
     */
    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[pics.length];
        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);//得到一个LinearLayout下面的每一个子元素
            dots[i].setEnabled(false);
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag,方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(true);
    }

    /**
     *设置当前view
     */
    private void setCurView(int position){
        if (position < 0 || position >= pics.length){
            return;
        }
        vp.setCurrentItem(position);
    }

    /**
     *设置当前指示点
     */
    private void setCurDot(int position){
        if (position < 0 || position > pics.length || currentIndex == position){
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("enter")){
            enterMainActivity();
            return;
        }
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    private void enterMainActivity() {
        Intent intent = new Intent(WelcomeGuideActivity.this,
                SplashActivity.class);
        startActivity(intent);
        SpUtils.putBoolean(WelcomeGuideActivity.this,Constants.FIRST_OPEN, false);
        finish();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            setCurDot(position);//设置底部小点选中状态
        }

        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
