package com.example.lyq.software.ui.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.example.lyq.software.ui.activity.ReleaseDetialActivity;
import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Release;
import com.example.lyq.software.utils.DateTimeUtil;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lyq on 2018/3/12.
 */

public class ReleaseAdapter extends RecyclerView.Adapter<ReleaseAdapter.ViewHolder> {

    private List<Release> mReleaseList;
    private List<Images> mImagesList;
    private List<Login> mUserList;
    private String mState;
    private Activity mActivity;
    String TAG = "result";

    static class ViewHolder extends RecyclerView.ViewHolder{

        View releaseView;
        ImageView ivImage;
        TextView tvDescript;
        TextView tvPrice;
        CircleImageView ciHead;
        TextView tvNick;
        TextView tvDate;
        ImageView ivImage2;
        ImageView ivImage3;

        public ViewHolder(View view) {
            super(view);
            releaseView = view;
            ciHead = (CircleImageView) view.findViewById(R.id.ci_head);
            tvNick = (TextView) view.findViewById(R.id.tv_nick);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            ivImage2 = (ImageView) view.findViewById(R.id.iv_image2);
            ivImage3 = (ImageView) view.findViewById(R.id.iv_image3);
            tvDescript = (TextView) view.findViewById(R.id.tv_descript);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
        }
    }

    public ReleaseAdapter(List<Release> releaseList, List<Images> imagesList, List<Login> userList, String state, Activity activity) {
        this.mReleaseList = releaseList;
        this.mImagesList = imagesList;
        this.mUserList = userList;
        this.mState = state;
        this.mActivity = activity;
        Log.e(TAG, "ReleaseAdapter: "+ mReleaseList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_release,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.releaseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();//案列第一行代码，RecyclerView的点击事件
                Release release = mReleaseList.get(position);
                Images images = mImagesList.get(position);
                Login user = mUserList.get(position);
                Intent intent = new Intent(mActivity, ReleaseDetialActivity.class);
                intent.putExtra("releaseData",release);
                intent.putExtra("imageData",images);
                intent.putExtra("userData",user);
                intent.putExtra("state",mState);
                mActivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Release release = mReleaseList.get(position);
        Images images = mImagesList.get(position);
        Login users = mUserList.get(position);
        Glide.with(mActivity)
                .load(Constants.BASE_URL + users.getHead())
                .into(holder.ciHead);
        Glide.with(mActivity)
                .load(Constants.BASE_URL + images.getImage1())
//                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(holder.ivImage);
        Log.e(TAG, "getImage1: " + images.getImage1());
        Glide.with(mActivity)
                .load(Constants.BASE_URL + images.getImage2())
//                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(holder.ivImage2);
        Glide.with(mActivity)
                .load(Constants.BASE_URL + images.getImage3())
//                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(holder.ivImage3);
        holder.tvNick.setText(users.getNick());
        holder.tvDescript.setText(release.getDescript());
        Date date = DateTimeUtil.strToDateHHMMSS(release.getDate());
        String timeLength = DateTimeUtil.formatFriendly(date);
        holder.tvDate.setText(timeLength);
        holder.tvPrice.setText(release.getPrice());
        Log.e(TAG, "mReleaseList: "+ mReleaseList.size() );
        Log.e(TAG, "mImagesList: "+ mImagesList.size() );
        Log.e(TAG, "mUserList: "+ mUserList.size());
    }

    @Override
    public int getItemCount() {
        return mReleaseList.size();
    }
}
