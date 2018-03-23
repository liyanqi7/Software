package com.example.lyq.software.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.AppActivity;
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
    private Activity mActivity;
    String TAG = "result";

    static class ViewHolder extends RecyclerView.ViewHolder{

        View releaseView;
        ImageView ivImage;
        TextView tvType;
        TextView tvDescript;
        TextView tvBegin;
        TextView tvEnd;
        TextView tvPrice;
        CircleImageView ciHead;
        TextView tvNick;
        private final TextView tvDate;
        private final ImageView ivImage2;
        private final ImageView ivImage3;

        public ViewHolder(View view) {
            super(view);
            releaseView = view;
            ciHead = (CircleImageView) view.findViewById(R.id.ci_head);
            tvNick = (TextView) view.findViewById(R.id.tv_nick);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            ivImage2 = (ImageView) view.findViewById(R.id.iv_image2);
            ivImage3 = (ImageView) view.findViewById(R.id.iv_image3);
//            tvType = (TextView) view.findViewById(R.id.tv_type);
            tvDescript = (TextView) view.findViewById(R.id.tv_descript);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
//            tvBegin = (TextView) view.findViewById(R.id.tv_begin);
//            tvEnd = (TextView) view.findViewById(R.id.tv_end);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
        }
    }

    public ReleaseAdapter(List<Release> releaseList, List<Images> imagesList, List<Login> userList, Activity activity) {
        mReleaseList = releaseList;
        mImagesList = imagesList;
        mUserList = userList;
        mActivity = activity;
        Log.e(TAG, "ReleaseAdapter: "+ mReleaseList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_release,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.releaseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Release release = mReleaseList.get(position);
                Images images = mImagesList.get(position);
                Login user = mUserList.get(position);
                Intent intent = new Intent(mActivity, ReleaseDetialActivity.class);
                intent.putExtra("releaseData",release);
                intent.putExtra("imageData",images);
                intent.putExtra("userData",user);
                mActivity.startActivity(intent);
//                Toast.makeText(mActivity, "我是" + release.getReleaseId()+release.getDescript(), Toast.LENGTH_SHORT).show();
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
        holder.tvNick.setText(users.getNick());
        Glide.with(mActivity)
                .load(Constants.BASE_URL + images.getImage1())
                .into(holder.ivImage);
        Glide.with(mActivity)
                .load(Constants.BASE_URL + images.getImage2())
                .into(holder.ivImage2);
        Glide.with(mActivity)
                .load(Constants.BASE_URL + images.getImage3())
                .into(holder.ivImage3);
//        holder.tvType.setText(release.getTypeTwo());
        holder.tvDescript.setText(release.getDescript());
        Date date = DateTimeUtil.strToDateHHMMSS(release.getDate());
        String timeLength = DateTimeUtil.formatFriendly(date);
        holder.tvDate.setText(timeLength);
//        holder.tvBegin.setText(release.getBeginTime());
//        holder.tvEnd.setText(release.getEndTime());
        holder.tvPrice.setText(release.getPrice());
        Log.e(TAG, "onBindViewHolder: "+ release.getDescript() );
    }

    @Override
    public int getItemCount() {
        return mReleaseList.size();
    }
}
