package com.example.lyq.software.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.example.lyq.software.utils.SpUtils;

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
        private final TextView tvCompany;
        private final TextView tvNature;
        private final TextView tvVolume;
        private final TextView tvAddress;
        private final TextView tvEnterShop;
        private final TextView tvCall;


        public ViewHolder(View view) {
            super(view);
            ivShop = (ImageView) view.findViewById(R.id.iv_shop);
            tvCompany = (TextView) view.findViewById(R.id.tv_company);
            tvNature = (TextView) view.findViewById(R.id.tv_nature);
            tvVolume = (TextView) view.findViewById(R.id.tv_volume);
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
            tvCall = (TextView) view.findViewById(R.id.tv_call);
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
        holder.tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + shop.getUserName());
                intent.setData(data);
                mActivity.startActivity(intent);
            }
        });
        holder.tvEnterShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("shopData",shop);
                intent.putExtra("userData",user);
                intent.putExtra("volumeData",volume);
                intent.putExtra("stateData","Browse");//判断进入店铺的方式，是通过直接进入，还是处理申请
                intent.setClass(mActivity, OrderProcessActivity.class);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

}
