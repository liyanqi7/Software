package com.example.lyq.software.ui.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyq on 2018/4/19.
 */

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> {
    private List<Example> mExampleList = new ArrayList<Example>();
    private Activity mActivity;

    public ExampleAdapter(List<Example> exampleList, FragmentActivity activity) {
        this.mExampleList = exampleList;
        this.mActivity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivImage;
        private final TextView tvDesign;

        public ViewHolder(View view) {
            super(view);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            tvDesign = (TextView) view.findViewById(R.id.tv_design);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_example,parent,false);
        final ViewHolder holder = new ViewHolder(view);
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
