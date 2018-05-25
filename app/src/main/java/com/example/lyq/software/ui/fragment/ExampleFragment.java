package com.example.lyq.software.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lyq.software.R;
import com.example.lyq.software.base.TokenSave;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.ExampleAdapter;
import com.example.lyq.software.ui.bean.Example;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;

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
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_example, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //使用adapter先初始化界面，再传值进adapter
        adapter = new ExampleAdapter(exampleList,getContext());
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        String url = Constants.BASE_URL + "/myExampleServlet";
        String userName = SpUtils.getShopName(getContext(),Constants.SHOPNAME);
        RequestBody body = new FormBody.Builder()
                .add("userName", userName)
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
        for (int i = 0; i < exampleArray.length(); i++) {
            Log.e("TAG", "parseJSONWithGSON: " + exampleArray.getJSONObject(i));
            //静态方法的使用不需要建立对象
            Example example = Example.setExample(exampleArray.getJSONObject(i));
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
