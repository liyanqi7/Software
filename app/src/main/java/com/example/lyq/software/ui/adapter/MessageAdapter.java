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
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.OrderProcessActivity;
import com.example.lyq.software.ui.bean.Order;
import com.example.lyq.software.utils.DateTimeUtil;
import com.example.lyq.software.utils.HttpUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lyq on 2018/3/20.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private List<Order> mOrderList;
    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View orderView;
        ImageView ivHead;
        TextView tvDescript;
        ImageView icCircle;
        private final TextView tvTime;

        public ViewHolder(View view) {
            super(view);
            orderView = view;
            ivHead = (ImageView) view.findViewById(R.id.iv_head);
            tvDescript = (TextView) view.findViewById(R.id.tv_descript);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            icCircle = (ImageView) view.findViewById(R.id.iv_circle);
        }
    }

    public MessageAdapter(List<Order> orderList, FragmentActivity activity) {
        mOrderList = orderList;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Order order = mOrderList.get(position);
        Log.e("debug", "onBindViewHolder: " + mOrderList.size());
        Glide.with(mActivity)
                .load(Constants.BASE_URL + order.getHead())
                .into(holder.ivHead);
        holder.tvDescript.setText(order.getDescript());
        Date date = DateTimeUtil.strToDateHHMMSS(order.getDate());
        String timeLength = DateTimeUtil.formatFriendly(date);
        holder.tvTime.setText(timeLength);
        if (order.getBrowse().equals("true")){
            holder.icCircle.setVisibility(View.INVISIBLE); //判断消息的浏览状态
        }
        holder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.icCircle.setVisibility(View.INVISIBLE);
                final String releaseId = order.getReleaseId();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        changeBrowse(releaseId);
                    }
                }).start();
                Intent intent = new Intent();
                intent.putExtra("applyName",order.getApplyName());
                intent.putExtra("releaseId",order.getReleaseId());
                intent.setClass(mActivity,OrderProcessActivity.class);
                mActivity.startActivity(intent);
            }
        });
    }

    private void changeBrowse(String releaseId) {
        String url = Constants.BASE_URL + "/changeBrowseServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("releaseId",releaseId)
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
