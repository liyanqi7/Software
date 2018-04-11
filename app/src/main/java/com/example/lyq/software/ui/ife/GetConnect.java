package com.example.lyq.software.ui.ife;

import okhttp3.RequestBody;

/**
 * Created by lyq on 2018/3/30.
 */

public interface GetConnect {

    public void connectServer(String url, RequestBody body);

    public void parseJson(String responseData);
}
