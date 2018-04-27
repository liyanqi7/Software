package com.example.lyq.software.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShopInformationFragment extends Fragment {

    private View view;
    private TextView tvDescript;
    private ImageView ivImg1;
    private ImageView ivImg2;
    private TextView tvAddress;
    private Shop shop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_information, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();
        String userName = bundle.getString("shopName");
        String url = Constants.BASE_URL + "/applyShopServlet";
        Log.e("TAG", "initData: "+userName);
//        String userName = SpUtils.getTokenId(getContext(),Constants.TOKENID);
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("userName",userName)
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    parseSHOPWithGSON(responseData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseSHOPWithGSON(String responseData) throws JSONException {
        JSONObject object = new JSONObject(responseData);
        JSONArray shopArray = object.getJSONArray("shopList");
        JSONArray userArray = object.getJSONArray("userList");
        JSONArray volumeArray = object.getJSONArray("countList");
        Log.e("TAG", "parseJSONWithGSON: " + shopArray);
        Log.e("TAG", "parseJSONWithGSON: " + userArray);
        Log.e("TAG", "parseJSONWithGSON: " + volumeArray);
        for (int i = 0; i < shopArray.length(); i++) {
            shop = new Shop();
            JSONObject obj = shopArray.getJSONObject(i);
            shop.setUserName(obj.getString("userName"));
            shop.setCompany(obj.getString("company"));
            shop.setDescript(obj.getString("descript"));
            shop.setProvince(obj.getString("province"));
            shop.setCity(obj.getString("city"));
            shop.setDetail(obj.getString("detail"));
            shop.setNature(obj.getString("nature"));
            shop.setImage1(obj.getString("image1"));
            shop.setImage2(obj.getString("image2"));
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvDescript.setText(shop.getDescript());
                tvAddress.setText(shop.getProvince()+"省" + shop.getCity() + "市" + shop.getDetail());
                Glide.with(getContext())
                        .load(Constants.BASE_URL + shop.getImage1())
                        .into(ivImg1);
                Glide.with(getContext())
                        .load(Constants.BASE_URL + shop.getImage2())
                        .into(ivImg2);
            }
        });
    }

    private void initView() {
        tvDescript = (TextView) view.findViewById(R.id.tv_descript);
        tvAddress = (TextView) view.findViewById(R.id.tv_address);
        ivImg1 = (ImageView) view.findViewById(R.id.iv_img1);
        ivImg2 = (ImageView) view.findViewById(R.id.iv_img2);
    }
}
