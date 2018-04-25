package com.example.lyq.software.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Register;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView register;
    private ImageView back;
    private Button login;
    private EditText username;
    private EditText password;
    private String url = Constants.BASE_URL + "/loginServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = (TextView) findViewById(R.id.register);
        back = (ImageView) findViewById(R.id.back);
        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        register.setOnClickListener(this);
        back.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                break;
            case R.id.register:
                startActivity(new Intent(this,RegisterActivity.class));
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            case R.id.login:
                requestLogin();
                break;
        }
    }

    private void requestLogin() {
        String name = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        JSONObject object = new JSONObject();
        try {
            object.put("name",name);
            object.put("pwd",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String content = String.valueOf(object);//转化成字符串
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),content);
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                });
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
        final Login ln = gson.fromJson(responseData, Login.class);
        if (ln.getResult().toString().equals("true")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoginActivity.this.finish();
                    SpUtils.putTokenId(getBaseContext(),Constants.TOKENID,ln.getTokenId().toString());
                    SpUtils.putNick(getBaseContext(),Constants.NICK,ln.getNick().toString());
                    SpUtils.putHead(getBaseContext(),Constants.HEAD,ln.getHead().toString());
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                    Toast.makeText(LoginActivity.this, "登录成功！" + SpUtils.getNick(getBaseContext(),Constants.NICK), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新登录!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void changeHead(Login ln) {
        Log.e("", "changeHead: " + ln.getTokenId().toString() );
        View view = LayoutInflater.from(this).inflate(R.layout.item_fragment_my_head,null,false);
        TextView name = (TextView) view.findViewById(R.id.username);
        name.setText(ln.getTokenId().toString());
    }
}
