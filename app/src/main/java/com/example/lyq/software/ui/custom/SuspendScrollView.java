package com.example.lyq.software.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lyq on 2018/4/4.
 */

public class SuspendScrollView extends ScrollView {
    private OnScrollListener onScrollListener;
    private int lastScrollY;//主要是用在用户手指离开SuspendScrollView，SuspendScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
    public SuspendScrollView(Context context) {
        super(context);
    }
    public SuspendScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public SuspendScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置滚动接口
     */
    public void setOnScrollListener(OnScrollListener onScrollListener){
        this.onScrollListener = onScrollListener;
    }

    /**
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int scrollY = SuspendScrollView.this.getScrollY();
            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if (lastScrollY != scrollY){
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(),5);
            }
            if (onScrollListener != null){
                onScrollListener.onScroll(scrollY);
            }
        };
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (onScrollListener != null){
            onScrollListener.onScroll(lastScrollY = this.getScrollY());
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(),20);
        }
        return super.onTouchEvent(ev);
    }

    public interface OnScrollListener {
        /**
         *回调方法，返回MyScrollView滑动的Y方向距离
         */
        void onScroll(int scrollY);
    }

}
