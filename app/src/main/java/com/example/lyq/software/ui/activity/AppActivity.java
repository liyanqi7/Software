package com.example.lyq.software.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;
import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Release;
import com.example.lyq.software.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class AppActivity extends BaseActivity {
    String url = Constants.BASE_URL + "/categoryServlet";
    String TAG = "result";
    private List<Release> releaseList = new ArrayList<Release>();
    private List<Images> imagesList = new ArrayList<Images>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ReleaseAdapter adapter = new ReleaseAdapter(releaseList,imagesList,AppActivity.this);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        FormBody formBody = new FormBody.Builder()
                .add("type", "APP开发")
                .build();
        HttpUtil.post(url, formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.e(TAG, "onResponse: " + responseData);
                Map<String,Object> map = null;
                try {
                    map = parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Map<String, Object> parseJSONWithGSON(String responseData) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        JSONObject object = new JSONObject(responseData);
        JSONArray arrayRelease = object.getJSONArray("releaseList");
        JSONArray arrayImage = object.getJSONArray("imageList");
        Release release = null;
        Images images = null;
        Log.e(TAG, "parseJSONWithGSON: " + arrayRelease );
        Log.e(TAG, "parseJSONWithGSON: " + arrayImage);
        for (int i = 0; i < arrayImage.length(); i++) {
            Log.e(TAG, "arrayImage: "+arrayImage.length() );
            images = new Images();
            JSONObject obj = arrayImage.getJSONObject(i);//注意代码的错误，误把arrayRelease，当作arrayImage使用！！！
            images.setReleaseId(obj.getString("releaseId"));
            images.setImage1(obj.getString("image1"));
            images.setImage2(obj.getString("image2"));
            images.setImage3(obj.getString("image3"));
            images.setImage4(obj.getString("image4"));
            images.setImage5(obj.getString("image5"));
            images.setImage6(obj.getString("image6"));
            images.setImage7(obj.getString("image7"));
            images.setImage8(obj.getString("image8"));
            images.setImage9(obj.getString("image9"));
            imagesList.add(images);
        }
        for (int i = 0; i < arrayRelease.length(); i++) {
            Log.e(TAG, "arrayRelease" + arrayRelease.length() );
            release = new Release();//创建对象循环与不循环有何差别？？？？？？？？
            JSONObject obj = arrayRelease.getJSONObject(i);
            release.setReleaseId(obj.getString("releaseId"));
            release.setUserName(obj.getString("userName"));
            release.setDescript(obj.getString("descript"));
            Log.e(TAG, "parseJSONWithGSON: "+release.getUserName() );
            release.setType(obj.getString("type"));
            release.setPrice(obj.getString("price"));
            release.setBeginTime(obj.getString("beginTime"));
            release.setEndTime(obj.getString("endTime"));
            releaseList.add(release);
//                Log.e(TAG, "parseJSONWithGSON: "+obj.getString("releaseId") );
//                Log.e(TAG, "parseJSONWithGSON: "+obj.getString("userName") );
//                Log.e(TAG, "parseJSONWithGSON: "+obj.getString("type") );
        }
        result.put("releaseList",releaseList);
        result.put("imageList",imagesList);
        return result;
    }
}
