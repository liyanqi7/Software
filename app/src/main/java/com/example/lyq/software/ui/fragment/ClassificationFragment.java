package com.example.lyq.software.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyq.software.R;
import com.example.lyq.software.ui.activity.AddRequirementsActivity;

public class ClassificationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classification, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startActivity(new Intent(getContext(),AddRequirementsActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
