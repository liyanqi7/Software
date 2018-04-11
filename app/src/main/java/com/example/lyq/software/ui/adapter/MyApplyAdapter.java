package com.example.lyq.software.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.MyApplyActivity;
import com.example.lyq.software.ui.activity.OrderProcessActivity;
import com.example.lyq.software.ui.bean.Order;
import com.example.lyq.software.utils.DateTimeUtil;
import com.example.lyq.software.utils.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lyq on 2018/3/29.
 */

public class MyApplyAdapter extends RecyclerView.Adapter<MyApplyAdapter.ViewHolder> implements View.OnClickListener {

    private List<Order> mOrderList ;
    private Activity mMyApplyActivity;
    private String applyName;
    private String releaseId;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_delete:
                doDelete();
//                Toast.makeText(, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_connect:
                Log.e("tip", "tv_connect: 已同意");
                break;
        }
    }

    private void doDelete() {
        String url = Constants.BASE_URL + "/deleteApplyServlet";
        RequestBody body = new FormBody.Builder()
                .add("applyName",applyName)
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

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDescript;
        TextView tvTime;
        TextView tvState;
        TextView tvDelete;
        TextView tvConnect;

        public ViewHolder(View view) {
            super(view);
            tvDescript = (TextView) view.findViewById(R.id.tv_descript);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvState = (TextView) view.findViewById(R.id.tv_state);
            tvDelete  = (TextView) view.findViewById(R.id.tv_delete);
            tvConnect = (TextView) view.findViewById(R.id.tv_connect);
        }
    }

    public MyApplyAdapter(List<Order> orderList) {
        this.mOrderList = orderList;
//        this.mMyApplyActivity = myApplyActivity;
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
        applyName = order.getApplyName();
        releaseId = order.getReleaseId();
        holder.tvDescript.setText(order.getDescript());
        Date date = DateTimeUtil.strToDateHHMMSS(order.getDate());
        String timeLength = DateTimeUtil.formatFriendly(date);
        String state = order.getState();
        holder.tvTime.setText(timeLength);
        holder.tvState.setText(state);
        if (state.equals("已同意")){
            holder.tvDelete.setVisibility(View.GONE);
            holder.tvConnect.setVisibility(View.VISIBLE);
            holder.tvConnect.setOnClickListener(this);
        }else {
            holder.tvDelete.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
