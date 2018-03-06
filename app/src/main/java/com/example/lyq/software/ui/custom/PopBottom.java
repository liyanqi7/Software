package com.example.lyq.software.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lyq.software.R;

/**
 * Created by lyq on 2018/1/12.
 */

public class PopBottom extends PopupWindow implements View.OnClickListener {

    private final View bottomView;
    private final TextView tvTakePhoto;
    private final TextView tvAlbum;
    private final TextView tvCancel;

    public PopBottom(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bottomView = inflater.inflate(R.layout.item_bottom_dialog,null);
        int width = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(bottomView);
        this.setWidth(width);//设置窗体的宽
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);//设置弹出窗体可点击
        this.setOutsideTouchable(true);
        this.update();//刷新状态
        tvTakePhoto = (TextView) bottomView.findViewById(R.id.tv_takePhoto);
        tvAlbum = (TextView) bottomView.findViewById(R.id.tv_album);
        tvCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
    }
    public void showPopBottom(View parent){
        if (!this.isShowing()){
            this.showAsDropDown(parent,parent.getLayoutParams().width,20);
        }else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                PopBottom.this.dismiss();
                break;
        }
    }
}
