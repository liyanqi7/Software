package com.example.lyq.software.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.ui.bean.Order;
import com.example.lyq.software.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lyq on 2018/3/29.
 */

public class MyApplyAdapter extends RecyclerView.Adapter<MyApplyAdapter.ViewHolder> {

    private List<Order> mOrderList ;
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDescript;
        TextView tvTime;
        TextView tvState;

        public ViewHolder(View view) {
            super(view);
            tvDescript = (TextView) view.findViewById(R.id.tv_descript);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvState = (TextView) view.findViewById(R.id.tv_state);
        }
    }

    public MyApplyAdapter(List<Order> orderList) {
        this.mOrderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_apply,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = mOrderList.get(position);
        holder.tvDescript.setText(order.getDescript());
        Date date = DateTimeUtil.strToDateHHMMSS(order.getDate());
        String timeLength = DateTimeUtil.formatFriendly(date);
        holder.tvTime.setText(timeLength);
        holder.tvState.setText(order.getState());
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
