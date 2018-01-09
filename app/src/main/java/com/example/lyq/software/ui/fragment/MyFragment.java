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

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.LoginActivity;
import com.example.lyq.software.ui.activity.PersonalActivity;
import com.example.lyq.software.ui.activity.SetActivity;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.utils.SpUtils;

public class MyFragment extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout head;
    private ImageView set;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        head = (LinearLayout) view.findViewById(R.id.head);
        set = (ImageView) view.findViewById(R.id.set);
        head.setOnClickListener(this);
        set.setOnClickListener(this);
        String tokenId = SpUtils.getTokenId(getContext(),Constants.TOKENID);
        Log.e("1", "onCreateView: " + tokenId);
        if (!tokenId.toString().isEmpty())
        {
            changeHead(tokenId);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("1", "onResume: ");
        refresh();
    }

    private void refresh() {
        view.invalidate();
    }

    private void changeHead(String tokenId) {
            TextView name = (TextView) view.findViewById(R.id.username);
            name.setText(tokenId.toString());
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
