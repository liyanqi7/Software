package com.example.lyq.software.base;

import com.example.lyq.software.ui.ife.GetConnect;
import com.example.lyq.software.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lyq on 2018/3/30.
 */

public abstract class ConnectServer implements GetConnect{

    @Override
    public void connectServer(String url, RequestBody body) {
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJson(responseData);
            }
        });
    }

//    @Override
//    public void parseJson(String responseData) {
//
//    }

}
