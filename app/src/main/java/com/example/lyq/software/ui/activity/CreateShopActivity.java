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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.PhotoPickerAdapter;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateShopActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etCompany;
    private EditText etProvince;
    private EditText etCity;
    private RadioGroup rgNature;
    private RadioButton rbEnterprise;
    private RadioButton rbPrivate;
    private Button register;
    private String nature;
    private String company;
    private String province;
    private String city;
    private String userName;
    private GridView gridView;
    private PhotoPickerAdapter adapter;
    private ArrayList<String> imgPaths = new ArrayList<>();
    private EditText etDescript;
    private EditText etDetail;
    private String descript;
    private String detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shop);
        intitView();
        adapter = new PhotoPickerAdapter(imgPaths);
        gridView.setAdapter(adapter);
    }

    private void intitView() {
        etDescript = (EditText) findViewById(R.id.et_descript);
        etCompany = (EditText) findViewById(R.id.et_company);
        etProvince = (EditText) findViewById(R.id.et_province);
        etCity = (EditText) findViewById(R.id.et_city);
        etDetail = (EditText) findViewById(R.id.et_detail);
        rgNature = (RadioGroup) findViewById(R.id.rg_nature);
        rbEnterprise = (RadioButton) findViewById(R.id.rb_enterprise);
        rbPrivate = (RadioButton) findViewById(R.id.rb_private);
        gridView = (GridView) findViewById(R.id.gridView);
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
                            .start(CreateShopActivity.this, PhotoPicker.REQUEST_CODE);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("imgPaths",imgPaths);
                    bundle.putInt("position",position);
                    Intent intent = new Intent(CreateShopActivity.this,EnlargePicActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,position);
                }
            }
        });
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);//注意:初始化时不能获取控件上的数据，否则EditText数据为空
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
            case R.id.register:
                if (rbEnterprise.isChecked()){
                    nature = "企业";
                }
                if (rbPrivate.isChecked()){
                    nature = "私人";
                }
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("店铺注册")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doPost();
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
        String url = Constants.BASE_URL + "/insertShopServlet";
        userName = SpUtils.getTokenId(getBaseContext(), Constants.TOKENID);
        descript = etDescript.getText().toString();
        company = etCompany.getText().toString();
        province = etProvince.getText().toString();
        city = etCity.getText().toString();
        detail = etDetail.getText().toString();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        // 添加上传的参数
        builder.addFormDataPart("userName", userName);
        builder.addFormDataPart("company",company);
        builder.addFormDataPart("descript", descript);
        builder.addFormDataPart("province",province);
        builder.addFormDataPart("city",city);
        builder.addFormDataPart("detail", detail);
        for (int i = 0; i < imgPaths.size(); i++) {
            builder.addFormDataPart("image"+i, imgPaths.get(i),
                    RequestBody.create(MediaType.parse("image/jpeg"), new File(imgPaths.get(i))));
        }
        RequestBody body = builder.build();
//        Log.e("TAG", "intitView: "+province );
//        Log.e("TAG", "intitView: "+nature);
//        JSONObject object = new JSONObject();
//        System.out.println(company);
//        try {
//            object.put("userName",userName);
//            object.put("company",company);
//            object.put("province",province);
//            object.put("city",city);
//            object.put("nature",nature);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String content = String.valueOf(object);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"),content);
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finish();
                overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CreateShopActivity.this, "店铺注册成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
