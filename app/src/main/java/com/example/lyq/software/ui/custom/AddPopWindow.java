package com.example.lyq.software.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.example.lyq.software.R;
import com.example.lyq.software.ui.activity.AddExampleActivity;

/**
 * Created by lyq on 2017/11/3.
 */

public class AddPopWindow extends PopupWindow implements View.OnClickListener {
    private View conentView;

    public AddPopWindow(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.item_shop_popup,null);
        int height = context.getWindowManager().getDefaultDisplay().getHeight();
        int width = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(conentView);
//        this.setWidth(width/2 + 50);//设置弹出窗体的宽
        this.setWidth(width/2 - 50);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);//设置弹出窗体可点击
        this.setOutsideTouchable(true);
        this.update();//刷新状态
//        ColorDrawable drawable = new ColorDrawable(0000000000);
        ColorDrawable drawable = new ColorDrawable(78050505);
        this.setBackgroundDrawable(drawable);
        this.setAnimationStyle(R.style.AnimationPreview);//设置弹出窗体动画效果
        LinearLayout llMessage = (LinearLayout) conentView.findViewById(R.id.ll_message);
        LinearLayout llAddExample = (LinearLayout) conentView.findViewById(R.id.ll_addExample);
        llMessage.setOnClickListener(this);
        llAddExample.setOnClickListener(this);
    }
    public void showPopupWindow(View parent){
        if (!this.isShowing()){
//            this.showAsDropDown(parent,parent.getLayoutParams().width/2,20);//以下拉方式显示popupwindow
            this.showAsDropDown(parent,parent.getLayoutParams().width/2,0);
        }else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_message:
                AddPopWindow.this.dismiss();
                break;
            case R.id.ll_addExample:
                v.getContext().startActivity(new Intent(v.getContext(),AddExampleActivity.class));
                AddPopWindow.this.dismiss();
                break;
            default:
                break;
        }
    }
}
