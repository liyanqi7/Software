package com.example.lyq.software.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;

public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this)
        ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                break;
        }
    }
}
