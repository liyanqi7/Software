package com.example.lyq.software.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lyq.software.R;
import java.util.List;

/**
 * Created by lyq on 2018/5/16.
 */

public class StorageAdapter extends ArrayAdapter<String> {

    private int recourseId;
    private  List mList;

    public StorageAdapter(@NonNull Context context, @LayoutRes int resource, List<String> list) {
        super(context, resource, list);
        this.recourseId = resource;
        this.mList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        TypeTwo typeTwo = getItem(position);
        String content = (String) mList.get(position);
        View view;
        ViewHolder viewHolder = null;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(recourseId,parent,false);
            viewHolder = new StorageAdapter.ViewHolder();
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_content);
            viewHolder.ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            view.setTag(viewHolder);//将ViewHolder存储在View中
        }else {
            view = convertView;
            viewHolder = (StorageAdapter.ViewHolder) view.getTag();//重新获取ViewHolder
        }
        viewHolder.tvContent.setText(content);
        return view;
    }

    class ViewHolder {
        TextView tvContent;
        ImageView ivIcon;
    }
}
