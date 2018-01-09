package com.example.lyq.software.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Register;
import com.example.lyq.software.utils.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private Button register;
    private EditText username;
    private EditText password;
    private String url = Constants.BASE_URL + "Software/registerServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = (ImageView) findViewById(R.id.back);
        register = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        back.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                break;
            case R.id.register:
                requestRegister();
                break;
        }
    }

    private void requestRegister() {
        String name = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        JSONObject object = new JSONObject();
        try {
            object.put("name",name);
            object.put("pwd",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String content = String.valueOf(object);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),content);
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    Log.e("okhttp", "onFailure: ",e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithGSON(responseData);
            }
        });
    }

    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        Register rs = gson.fromJson(responseData, Register.class);
        if (rs.getResult().toString().equals("true")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RegisterActivity.this.finish();
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                    Toast.makeText(RegisterActivity.this, "注册成功,请登录!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegisterActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
