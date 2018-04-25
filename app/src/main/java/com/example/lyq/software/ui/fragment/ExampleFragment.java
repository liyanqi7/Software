package com.example.lyq.software.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ExampleAdapter;
import com.example.lyq.software.ui.bean.Example;
import com.example.lyq.software.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ExampleFragment extends Fragment {
    public List<Example> exampleList = new ArrayList<Example>();
    private ExampleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_example, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ExampleAdapter(exampleList,getActivity());
        recyclerView.setAdapter(adapter);
        initData();
        return view;
    }

    private void initData() {
        String url = Constants.BASE_URL + "/myExampleServlet";
        RequestBody body = new FormBody.Builder()
                .add("userName", "17")
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    parseJSONWithGSON(responseData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseJSONWithGSON(String responseData) throws JSONException {
        exampleList.clear();
        JSONObject object = new JSONObject(responseData);
        JSONArray exampleArray = object.getJSONArray("exampleList");
        Log.e("TAG", "parseJSONWithGSON: "+exampleArray );
//        shopList.addAll((Collection<? extends Shop>) shopArray);
        Example example = null;
        for (int i = 0; i < exampleArray.length(); i++) {
            example = new Example();
            JSONObject obj = exampleArray.getJSONObject(i);
            example.setUserName(obj.getString("userName"));
            example.setTheme(obj.getString("theme"));
            example.setType(obj.getString("type"));
            example.setPrice(obj.getString("price"));
            example.setSystem(obj.getString("system"));
            example.setDesign(obj.getString("design"));
            example.setImage1(obj.getString("image1"));
            example.setImage2(obj.getString("image2"));
            example.setImage3(obj.getString("image3"));
            example.setImage4(obj.getString("image4"));
            example.setImage5(obj.getString("image5"));
            example.setImage6(obj.getString("image6"));
            example.setImage7(obj.getString("image7"));
            example.setImage8(obj.getString("image8"));
            example.setImage9(obj.getString("image9"));
            exampleList.add(example);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

}
