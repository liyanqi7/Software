package com.example.lyq.software.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyq.software.R;
import com.example.lyq.software.ui.bean.Requirement;

import java.util.List;

/**
 * Created by lyq on 2018/1/22.
 */

public class AddRequirementAdapter extends ArrayAdapter<Requirement>{

    private int recourseId;

    public AddRequirementAdapter(Context context, int textViewResouceId, List<Requirement> objects){
        super(context,textViewResouceId,objects);
        recourseId = textViewResouceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Requirement requirement = getItem(position);//获取当前项的Requirement实例
        View view;
        ViewHolder viewHolder = null;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(recourseId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvType = (TextView) view.findViewById(R.id.tv_type);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_content);
            viewHolder.ivEnter = (ImageView) view.findViewById(R.id.iv_enter);
            view.setTag(viewHolder);//将ViewHolder存储在View中
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }
        viewHolder.tvType.setText(requirement.getType());
        viewHolder.tvContent.setText(requirement.getContent());
        viewHolder.ivEnter.setImageResource(requirement.getImg());
        return view;
    }

    class ViewHolder {
        TextView tvType;
        TextView tvContent;
        ImageView ivEnter;
    }
}
