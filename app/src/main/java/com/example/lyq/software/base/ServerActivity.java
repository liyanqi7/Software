package com.example.lyq.software.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import com.example.lyq.software.R;
import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Release;
import com.example.lyq.software.ui.bean.User;
import com.example.lyq.software.utils.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class ServerActivity extends Activity {

    String TAG = "result";
    public List<Release> releaseList = new ArrayList<Release>();
    public List<Images> imageList = new ArrayList<Images>();
    public List<User> userList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 设置状态栏的样式
         */
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.text_black_2));
        }
        setContentView(R.layout.activity_server);
    }

    public void initServerData() {
        HttpUtil.post(getURL(), getBody(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                Log.e(TAG, "onResponse: " + responseData);
                if (TextUtils.isEmpty(responseData)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            promptUI();
                        }
                    });
                }
                try {
                    parseJSONToEntity(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void parseJSONToEntity(String responseData) throws Exception {
        releaseList.clear();
        imageList.clear();
        userList.clear();//!!!!!!!!! 注释:list不能写成list = null,这样将改变地址指向
        JSONObject object = new JSONObject(responseData);
        JSONArray arrayRelease = object.getJSONArray("releaseList");
        JSONArray arrayImage = object.getJSONArray("imageList");
        JSONArray arrayUser = object.getJSONArray("userList");
        for (int i = 0; i < arrayRelease.length(); i++) {
            Release release = Release.setRelease(arrayRelease.getJSONObject(i));
            releaseList.add(release);
        }
        for (int i = 0; i < arrayImage.length(); i++) {
            Images image = Images.setImages(arrayImage.getJSONObject(i));
            imageList.add(image);
        }
        for (int i = 0; i < arrayUser.length(); i++) {
            User user = User.setUser(arrayUser.getJSONObject(i));
            userList.add(user);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //实际上调用子类中实现updateUI的方法
                updateUI();
            }
        });
    }

    public abstract String getURL();

    public abstract RequestBody getBody();

    public abstract void updateUI();

    public abstract void promptUI();

}
