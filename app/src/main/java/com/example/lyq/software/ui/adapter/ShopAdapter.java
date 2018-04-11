package com.example.lyq.software.ui.adapter;

import android.app.Activity;
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
import com.example.lyq.software.ui.activity.OrderProcessActivity;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.ui.bean.Volume;

import java.util.List;

/**
 * Created by lyq on 2018/4/9.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<Shop> mShopList;
    private List<Login> mUserList;
    private List<Volume> mVolumeList;
    private Activity mActivity;

    public ShopAdapter(List<Shop> shopList, List<Login> userList, List<Volume> volumeList, Activity activity) {
        this.mShopList = shopList;
        this.mUserList = userList;
        this.mVolumeList = volumeList;
        this.mActivity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivShop;
        TextView tvCompany;
        private final TextView tvNature;
        private final TextView tvVolume;
        private final TextView tvAddress;
        private final TextView tvEnterShop;


        public ViewHolder(View view) {
            super(view);
            ivShop = (ImageView) view.findViewById(R.id.iv_shop);
            tvCompany = (TextView) view.findViewById(R.id.tv_company);
            tvNature = (TextView) view.findViewById(R.id.tv_nature);
            tvVolume = (TextView) view.findViewById(R.id.tv_volume);
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
            tvEnterShop = (TextView) view.findViewById(R.id.tv_enterShop);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_shop,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Shop shop = mShopList.get(position);
        final Login user = mUserList.get(position);
        final Volume volume = mVolumeList.get(position);
        holder.tvCompany.setText(shop.getCompany());
        holder.tvNature.setText(shop.getNature());
        holder.tvVolume.setText("好评率99% 总共成交量" + volume.getSum() + "单");
        holder.tvAddress.setText(shop.getProvince() + "省" + shop.getCity() + "市" + "江宁区麒麟工业园区");
        Glide.with(mActivity)
                .load(Constants.BASE_URL + user.getHead())
                .into(holder.ivShop);
        holder.tvEnterShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("shopData",shop);
                intent.putExtra("userData",user);
                intent.putExtra("volumeData",volume);
                intent.putExtra("stateData","Browse");
                intent.setClass(mActivity, OrderProcessActivity.class);
                mActivity.startActivity(intent);
            }
        });
//        Log.e("TAG", "mReleaseList: "+ mShopList.size() );
//        Log.e("TAG", "mImagesList: "+ mVolumeList.size() );
//        Log.e("TAG", "mUserList: "+ mUserList.size());
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

}
