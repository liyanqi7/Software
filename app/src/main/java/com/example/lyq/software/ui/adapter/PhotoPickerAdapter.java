package com.example.lyq.software.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.lyq.software.R;
import com.example.lyq.software.utils.BitmapUtil;

import java.util.ArrayList;

/**
 * Created by lyq on 2018/3/5.
 */

public class PhotoPickerAdapter extends BaseAdapter{

    private ArrayList<String> listPath;

    public PhotoPickerAdapter(ArrayList<String> lisPath) {
        this.listPath = lisPath;
    }

    @Override
    public int getCount() {
        if (listPath.size() == 9){
            return listPath.size();
        } else {
            return listPath.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return listPath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_pics, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == listPath.size()) {
            holder.image.setImageResource(R.drawable.ic_add_pic);
            if (position == 9) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.image.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFile(listPath.get(position), 500, 500));
        }
        return convertView;
    }
    public static class ViewHolder {
        ImageView image;
    }
}
