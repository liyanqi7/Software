package com.example.lyq.software.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.LoginActivity;
import com.example.lyq.software.ui.activity.PersonalActivity;
import com.example.lyq.software.ui.activity.SetActivity;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.utils.SpUtils;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.lyq.software.lib.Constants.BASE_URL;

public class MyFragment extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout head;
    private ImageView set;
    private TextView name;
    private CircleImageView ivHead;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        head = (LinearLayout) view.findViewById(R.id.head);
        set = (ImageView) view.findViewById(R.id.set);
        name = (TextView) view.findViewById(R.id.username);
        ivHead = (CircleImageView) view.findViewById(R.id.iv_head);
        head.setOnClickListener(this);
        set.setOnClickListener(this);
//        String tokenId = SpUtils.getTokenId(getContext(),Constants.TOKENID);
//        Log.e("1", "onCreateView: " + tokenId);
//        if (!tokenId.toString().isEmpty())
//        {
//            String nick = SpUtils.getNick(getContext(),Constants.NICK);
//            changeHead(nick);
//        }
//        ！！！！！！！！！！！！！！！！！！！Fragment与Activity交互的生命周期问题
        return view;
    }

    //Fragment的生命周期
    @Override
    public void onResume() {
        super.onResume();
        String tokenId = SpUtils.getTokenId(getContext(),Constants.TOKENID);
        String head = SpUtils.getHead(getContext(),Constants.HEAD);
        Log.e("1", "onCreateView: " + tokenId);
        Log.e("1", "head: " + head);
        if (!tokenId.toString().isEmpty())
        {
            String nick = SpUtils.getNick(getContext(),Constants.NICK);
            name.setText(nick);
        }else {
            name.setText("请先注册或/登录");
        }
        //如果上传了头像，显示头像
        if (!head.toString().isEmpty()){
            Glide.with(getContext())
                    .load(BASE_URL + head)
                    .into(ivHead);
        }else {
            ivHead.setImageResource(R.mipmap.icon_login);
        }
    }

    private void refresh() {
        view.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head:
                String tokenId = SpUtils.getTokenId(getContext(), Constants.TOKENID);
                if (tokenId.toString().isEmpty())
                {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                }else {
                    startActivity(new Intent(getContext(), PersonalActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                }
                break;
            case R.id.set:
                startActivity(new Intent(new Intent(getContext(), SetActivity.class)));
                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
        }
    }
}
