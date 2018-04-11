package com.example.lyq.software.ui.ife;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by lyq on 2018/4/4.
 */

public interface GetUpload {

    String getURL();
    RequestBody getBody();
    void getServerData(String url, RequestBody body);
    void updateUI();
}
