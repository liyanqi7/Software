package com.example.lyq.software.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by lyq on 2018/1/18.
 */

public class HomeBannerAdapter extends PagerAdapter{
    int[] imgs;
    Context context;
    public HomeBannerAdapter(int[] images,Context context){
        this.imgs = images;
        this.context = context;
    }

    /**
     * 获取View的总数
     * @return View总数
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     *确认View与实例对象是否相互对应。ViewPager内部用于获取View对应的ItemInfo。
     * @param view ViewPager显示的View内容
     * @param object 在instantiateItem中提交给ViewPager进行保存的实例对象
     * @return 是否相互对应
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 为给定的位置创建相应的View。创建View之后,需要在该方法中自行添加到container中。
     * @param container ViewPager本身
     * @param position 给定的位置
     * @return 提交给ViewPager进行保存的实例对象
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int pos = position % imgs.length;
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(imgs[pos]);
        container.addView(imageView);
        return imageView;
    }

     /**
     * ViewPager调用该方法来通知PageAdapter当前ViewPager显示的主要项,提供给用户对主要项进行操作的方法。
     * @param container ViewPager本身
     * @param position 给定的位置
     * @param object 在instantiateItem中提交给ViewPager进行保存的实例对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}