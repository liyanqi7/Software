package com.example.lyq.software.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.PhotoPickerAdapter;
import com.example.lyq.software.utils.DateTimeUtil;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddExampleActivity extends AppCompatActivity implements View.OnClickListener {

    private GridView gridView;
    private PhotoPickerAdapter adapter;
    private ArrayList<String> imgPaths = new ArrayList<>();
    private EditText etDesign;
    private EditText etTheme;
    private EditText etType;
    private EditText etPrice;
    private EditText etSystem;
    private Button btnSubmit;
    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_example);
        initView();
        adapter = new PhotoPickerAdapter(imgPaths);
        gridView.setAdapter(adapter);
    }

    private void initView() {
        etDesign = (EditText) findViewById(R.id.et_design);
        etTheme = (EditText) findViewById(R.id.et_theme);
        etType = (EditText) findViewById(R.id.et_type);
        etPrice = (EditText) findViewById(R.id.et_price);
        etSystem = (EditText) findViewById(R.id.et_system);
        gridView = (GridView) findViewById(R.id.gridView);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imgPaths.size()){
                    PhotoPicker.builder()//点击添加图片时，调用PhotoPicker,添加图片的position是等于imgPaths.size()的
                            .setPhotoCount(9)
                            .setShowCamera(true)
                            .setSelected(imgPaths)
                            .setShowGif(true)
                            .setPreviewEnabled(true)
                            .start(AddExampleActivity.this, PhotoPicker.REQUEST_CODE);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("imgPaths",imgPaths);
                    bundle.putInt("position",position);
                    Intent intent = new Intent(AddExampleActivity.this,EnlargePicActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,position);
                }
            }
        });
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                imgPaths.clear();
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                imgPaths.addAll(photos);//将图片路径添加到imgPaths中去
                adapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_OK && requestCode >= 0 && requestCode <= 8) {
            imgPaths.remove(requestCode);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("确认发布")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doPost();
//                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
        }
    }

    private void doPost() {
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//        // 添加上传的参数
//        builder.addFormDataPart("userName", SpUtils.getTokenId(getBaseContext(), Constants.TOKENID));
//        builder.addFormDataPart("design",etDesign.getText().toString());
//        builder.addFormDataPart("theme",etTheme.getText().toString());
//        builder.addFormDataPart("type",etType.getText().toString());
//        builder.addFormDataPart("price",etPrice.getText().toString());
//        builder.addFormDataPart("system",etSystem.getText().toString());
//        Log.e(TAG, "doPost: "+ etDesign.getText().toString());
//        Log.e(TAG, "doPost: "+ etSystem.getText().toString());
//        // 添加上传图片
//        for (int i = 0; i < imgPaths.size(); i++) {
//            builder.addFormDataPart("image"+i, imgPaths.get(i),
//                    RequestBody.create(MediaType.parse("image/jpeg"), new File(imgPaths.get(i))));
//        }
//        RequestBody requestBody = builder.build();
//        Request.Builder reqBuilder = new Request.Builder();
//        Request request = reqBuilder
//                .url(Constants.BASE_URL + "/insertExampleServlet")
//                .post(requestBody)
//                .build();
//        try{
//            Response response = mOkHttpClient.newCall(request).execute();
//            Log.e("TAG", "响应码 " + response.code());
//            if (response.isSuccessful()) {
//                String resultValue = response.body().string();
//                Log.d("TAG", "响应体 " + response.code());
////                return resultValue;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        return result;
        String url = Constants.BASE_URL + "/insertExampleServlet";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        // 添加上传的参数
        builder.addFormDataPart("userName", SpUtils.getTokenId(getBaseContext(), Constants.TOKENID));
        builder.addFormDataPart("design",etDesign.getText().toString());
        builder.addFormDataPart("theme",etTheme.getText().toString());
        builder.addFormDataPart("type",etType.getText().toString());
        builder.addFormDataPart("price",etPrice.getText().toString());
        builder.addFormDataPart("system",etSystem.getText().toString());
        Log.e(TAG, "doPost: "+ etDesign.getText().toString());
        Log.e(TAG, "doPost: "+ etSystem.getText().toString());
        // 添加上传图片
        for (int i = 0; i < imgPaths.size(); i++) {
            builder.addFormDataPart("image"+i, imgPaths.get(i),
                    RequestBody.create(MediaType.parse("image/jpeg"), new File(imgPaths.get(i))));
        }
        RequestBody requestBody = builder.build();
        HttpUtil.post(url, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG", "onResponse: example"+response.code() );
            }
        });
    }

}
