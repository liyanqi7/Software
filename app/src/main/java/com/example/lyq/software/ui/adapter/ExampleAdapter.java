package com.example.lyq.software.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.ExampleDetailActivity;
import com.example.lyq.software.ui.bean.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyq on 2018/4/19.
 */

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> {
    private List<Example> mExampleList = new ArrayList<Example>();
    private Context mActivity;

    public ExampleAdapter(List<Example> exampleList, Context activity) {
        this.mExampleList = exampleList;
        this.mActivity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivImage;
        private final TextView tvDesign;

        public ViewHolder(View view) {
            super(view);
            View itemView = view;
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            tvDesign = (TextView) view.findViewById(R.id.tv_design);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_example,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //案列第一行代码，RecyclerView的点击事件
                int position = holder.getAdapterPosition();
                Example example = mExampleList.get(position);
                Intent intent = new Intent(mActivity, ExampleDetailActivity.class);
                intent.putExtra("exampleData",example);
                mActivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Example example = mExampleList.get(position);
        holder.tvDesign.setText(example.getTheme());
        Glide.with(mActivity)
                .load(Constants.BASE_URL + example.getImage1())
                .into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
