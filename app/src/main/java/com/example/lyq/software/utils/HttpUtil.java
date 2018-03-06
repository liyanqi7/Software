package com.example.lyq.software.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lyq on 2018/1/4.
 */

public class HttpUtil {
    public static void post(String url, RequestBody body, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
