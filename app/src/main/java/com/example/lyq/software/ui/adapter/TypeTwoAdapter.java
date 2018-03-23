package com.example.lyq.software.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.lyq.software.R;
import com.example.lyq.software.ui.bean.TypeTwo;
import java.util.List;

/**
 * Created by lyq on 2018/3/15.
 */

public class TypeTwoAdapter extends ArrayAdapter<TypeTwo> {
    private int recourseId;

    public TypeTwoAdapter(Context context, int textViewRecourceId, List<TypeTwo> objects) {
        super(context,textViewRecourceId,objects);
        this.recourseId = textViewRecourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TypeTwo typeTwo = getItem(position);
        View view;
        ViewHolder viewHolder = null;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(recourseId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(viewHolder);//将ViewHolder存储在View中
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }
        viewHolder.tvContent.setText(typeTwo.getTypeTwo());
        return view;
    }

    class ViewHolder {
        TextView tvContent;
    }
}
