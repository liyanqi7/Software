package com.example.lyq.software.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shop);
        intitView();
    }

    private void intitView() {
        etCompany = (EditText) findViewById(R.id.et_company);
        etProvince = (EditText) findViewById(R.id.et_province);
        etCity = (EditText) findViewById(R.id.et_city);
        rgNature = (RadioGroup) findViewById(R.id.rg_nature);
        rbEnterprise = (RadioButton) findViewById(R.id.rb_enterprise);
        rbPrivate = (RadioButton) findViewById(R.id.rb_private);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);//注意:初始化时不能获取控件上的数据，否则EditText数据为空
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
        userName = SpUtils.getTokenId(getBaseContext(), Constants.TOKENID);
        company = etCompany.getText().toString();
        province = etProvince.getText().toString();
        city = etCity.getText().toString();
        Log.e("TAG", "intitView: "+province );
        Log.e("TAG", "intitView: "+nature);
        String url = Constants.BASE_URL + "/insertShopServlet";
        JSONObject object = new JSONObject();
        System.out.println(company);
        try {
            object.put("userName",userName);
            object.put("company",company);
            object.put("province",province);
            object.put("city",city);
            object.put("nature",nature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String content = String.valueOf(object);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),content);
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
