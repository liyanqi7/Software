package com.example.lyq.software.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.PhotoPickerAdapter;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

public class AddRequirementsActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvType;
    private ImageView ivBack;
    private TextView tvBegin;
    private static int BEGIN = 1;
    private static int END = 2;
    private TextView tvEnd;
    private GridView gridView;
    private ArrayList<String> imgPaths = new ArrayList<>();
    private PhotoPickerAdapter adapter;
    private String TAG = "test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_requirements);
        initView();
        initParameter();
    }

    private void initView() {
        tvType = (TextView) findViewById(R.id.tv_type);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvBegin = (TextView) findViewById(R.id.tv_begin);
        tvEnd = (TextView) findViewById(R.id.tv_end);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new PhotoPickerAdapter(imgPaths);
        ivBack.setOnClickListener(this);
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //点击gridView中的一项
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imgPaths.size()){
                    PhotoPicker.builder()
                            .setPhotoCount(9)
                            .setShowCamera(true)
                            .setSelected(imgPaths)
                            .setShowGif(true)
                            .setPreviewEnabled(true)
                            .start(AddRequirementsActivity.this, PhotoPicker.REQUEST_CODE);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("imgPaths",imgPaths);
                    bundle.putInt("position",position);
                    Intent intent = new Intent(AddRequirementsActivity.this,EnlargePicActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,position);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                Log.e(TAG, "onActivityResult: " + imgPaths.size() );
                imgPaths.clear();
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                imgPaths.addAll(photos);//将图片路径添加到imgPaths中去
                for (int i = 0; i < imgPaths.size(); i++) {
//                    Log.e(TAG, "onActivityResult: "+ imgPaths.get(i) );
                }
                adapter.notifyDataSetChanged();
            }
        }

        if (resultCode == RESULT_OK && requestCode >= 0 && requestCode <= 8) {
            imgPaths.remove(requestCode);
            adapter.notifyDataSetChanged();
        }
    }

    private void initParameter() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        tvType.setText(type);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                this.finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            break;
            case R.id.tv_begin:
                selectDate(BEGIN);
                break;
            case R.id.tv_end:
                selectDate(END);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectDate(final int result) {
        Calendar c = Calendar.getInstance();//获取当前日期
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (result == BEGIN){
                    tvBegin.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                } else {
                    tvEnd.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
//                Toast.makeText(AddRequirementsActivity.this,year + "-" + (month + 1) + "-" + dayOfMonth,Toast.LENGTH_SHORT).show();
            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }
}
