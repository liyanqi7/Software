package com.example.lyq.software.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;

import java.io.File;
import java.util.Calendar;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.PhotoPickerAdapter;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.lyq.software.lib.Constants.BASE_URL;

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
    private Button btnSubmit;
    private EditText etDescript;
    private EditText etPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_requirements);
        initView();
        initParameter();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etDescript = (EditText) findViewById(R.id.et_descript);
        tvType = (TextView) findViewById(R.id.tv_type);
        etPrice = (EditText) findViewById(R.id.et_price);
        tvBegin = (TextView) findViewById(R.id.tv_begin);
        tvEnd = (TextView) findViewById(R.id.tv_end);
        gridView = (GridView) findViewById(R.id.gridView);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        adapter = new PhotoPickerAdapter(imgPaths);
        ivBack.setOnClickListener(this);
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        gridView.setAdapter(adapter);
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
                adapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_OK && requestCode >= 0 && requestCode <= 8) {
            imgPaths.remove(requestCode);
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < imgPaths.size(); i++) {
            Log.e(TAG, "onActivityResult: "+ imgPaths.get(i) );
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
            case R.id.btn_submit:
                uploadImage();
                break;
            default:
                break;
        }
    }

    private void uploadImage() {
        new NetworkTask().execute();
    }

    /**
     * 访问网络AsyncTask,访问网络在子线程进行并返回主线程通知访问的结果
     */
    class NetworkTask extends AsyncTask<String, Integer, String> {

        //将在执行实际的后台操作前被UI 线程调用
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //onPreExecute()方法执行后马上执行，该方法运行在后台线程中
        @Override
        protected String doInBackground(String... params) {
            return doPost();
        }

        @Override
        protected void onPostExecute(String result) {
            if(!"error".equals(result)) {
//                SpUtils.putHead(getBaseContext(),Constants.HEAD,result);
//                Log.i(TAG, "图片地址 " + BASE_URL + result);
//                Glide.with(getBaseContext())
//                        .load(BASE_URL + result)
//                        .into(ivHead);
            }
        }
    }

    private String doPost() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String result = "error";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        // 添加上传的参数
        builder.addFormDataPart("tokenId", SpUtils.getTokenId(getBaseContext(),Constants.TOKENID));
        builder.addFormDataPart("descript",etDescript.getText().toString());
        builder.addFormDataPart("type",tvType.getText().toString());
        builder.addFormDataPart("price",etPrice.getText().toString());
        builder.addFormDataPart("beginTime",tvBegin.getText().toString());
        builder.addFormDataPart("endTime",tvEnd.getText().toString());
        // 添加上传图片
        for (int i = 0; i < imgPaths.size(); i++) {
            Log.e(TAG, "doPost: "+ imgPaths.get(i));
            builder.addFormDataPart("image"+i, imgPaths.get(i),
                    RequestBody.create(MediaType.parse("image/jpeg"), new File(imgPaths.get(i))));
        }
        RequestBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        Request request = reqBuilder
                .url(Constants.BASE_URL + "/releaseServlet")
                .post(requestBody)
                .build();
        Log.e(TAG, "请求地址 " + Constants.BASE_URL + "/releaseServlet");
        try{
            Response response = mOkHttpClient.newCall(request).execute();
            Log.d(TAG, "响应码 " + response.code());
            if (response.isSuccessful()) {
                String resultValue = response.body().string();
                Log.d(TAG, "响应体 " + resultValue);
                return resultValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
