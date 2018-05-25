package com.example.lyq.software.ui.ife;

import android.util.Log;

import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Release;
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

/**
 * Created by lyq on 2018/4/4.
 */

public interface ShopService {

    public String getURL();

    public RequestBody getBody();

    public void updateUI();

    public void promptUI();

}
