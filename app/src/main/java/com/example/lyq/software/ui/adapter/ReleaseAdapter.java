package com.example.lyq.software.ui.adapter;

import android.app.Activity;
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
import com.example.lyq.software.ui.activity.AppActivity;
import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Release;

import java.util.List;

/**
 * Created by lyq on 2018/3/12.
 */

public class ReleaseAdapter extends RecyclerView.Adapter<ReleaseAdapter.ViewHolder> {

    private List<Release> mReleaseList;
    private List<Images> mImagesList;
    private Activity mActivity;
    String TAG = "result";
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView tvType;
        TextView tvDescript;
        TextView tvBegin;
        TextView tvEnd;
        TextView tvPrice;

        public ViewHolder(View view) {
            super(view);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            tvType = (TextView) view.findViewById(R.id.tv_type);
            tvDescript = (TextView) view.findViewById(R.id.tv_descript);
            tvBegin = (TextView) view.findViewById(R.id.tv_begin);
            tvEnd = (TextView) view.findViewById(R.id.tv_end);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
        }
    }

    public ReleaseAdapter(List<Release> releaseList, List<Images> imagesList, AppActivity appActivity) {
        mReleaseList = releaseList;
        mImagesList = imagesList;
        mActivity = appActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_release,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Release release = mReleaseList.get(position);
        Images images = mImagesList.get(position);
        Glide.with(mActivity)
                .load(Constants.BASE_URL + images.getImag1())
                .into(holder.ivImage);
        holder.tvType.setText(release.getType());
        holder.tvDescript.setText(release.getDescript());
        holder.tvBegin.setText(release.getBeginTime());
        holder.tvEnd.setText(release.getEndTime());
        holder.tvPrice.setText(release.getPrice());
        Log.e(TAG, "onBindViewHolder: "+release.getDescript() );
    }

    @Override
    public int getItemCount() {
        return mReleaseList.size();
    }
}
