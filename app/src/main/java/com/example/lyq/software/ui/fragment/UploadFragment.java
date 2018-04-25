package com.example.lyq.software.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.WebActivity;
import com.example.lyq.software.ui.adapter.ReleaseAdapter;
import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Release;
import com.example.lyq.software.ui.ife.GetUpload;
import com.example.lyq.software.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadFragment extends Fragment{

    public List<Release> releaseList = new ArrayList<Release>();
    public List<Images> imagesList = new ArrayList<Images>();
    public List<Login> userList = new ArrayList<Login>();
    private ReleaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_upload, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        String state = "upload";
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ReleaseAdapter(releaseList,imagesList,userList,state,getActivity());
        recyclerView.setAdapter(adapter);
        initData();
        return view;
    }

    private void initData() {
        String url = Constants.BASE_URL + "/allUploadServlet";
        FormBody body = new FormBody.Builder()
                .add("state", "发布中")
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                try {
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void parseJSONWithGSON(String responseData) throws Exception{
        releaseList.clear();
        imagesList.clear();
        userList.clear();//!!!!!!!!! 注释:list不能写成list = null,这样将改变地址指向
        JSONObject object = new JSONObject(responseData);
        JSONArray arrayRelease = object.getJSONArray("releaseList");
        JSONArray arrayImage = object.getJSONArray("imageList");
        JSONArray arrayUser = object.getJSONArray("userList");
        Release release = null;
        Images images = null;
        Login user = null;
        for (int i = 0; i < arrayImage.length(); i++) {
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
        for (int i = 0; i < arrayUser.length(); i++){
            user = new Login();//创建对象循环与不循环有何差别？？？？？？？？
            JSONObject obj = arrayUser.getJSONObject(i);
            user.setNick(obj.getString("nick"));
            user.setHead(obj.getString("head"));
            userList.add(user);
            Log.e("tag", "parseJSONWithGSON: " + userList );
        }
        for (int i = 0; i < arrayRelease.length(); i++) {
            release = new Release();//创建对象循环与不循环有何差别？？？？？？？？
            JSONObject obj = arrayRelease.getJSONObject(i);
            release.setReleaseId(obj.getString("releaseId"));
            release.setUserName(obj.getString("userName"));
            release.setDescript(obj.getString("descript"));;
            release.setType(obj.getString("type"));
            release.setTypeTwo(obj.getString("typeTwo"));
            release.setPrice(obj.getString("price"));
            release.setDate(obj.getString("date"));
            release.setBeginTime(obj.getString("beginTime"));
            release.setEndTime(obj.getString("endTime"));
            releaseList.add(release);
        }
        getActivity().runOnUiThread(new Runnable() {  //启动主线程进行UI操作
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
