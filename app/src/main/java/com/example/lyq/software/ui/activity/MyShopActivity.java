package com.example.lyq.software.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Shop;
import com.example.lyq.software.ui.bean.Volume;
import com.example.lyq.software.ui.custom.AddPopWindow;
import com.example.lyq.software.ui.fragment.ExampleFragment;
import com.example.lyq.software.ui.fragment.MyApplyFragment;
import com.example.lyq.software.ui.fragment.ShopHomeFragment;
import com.example.lyq.software.ui.fragment.ShopInformationFragment;
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

public class MyShopActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvHomePage;
    private TextView tvOrder;
    private TextView tvDeal;
    private TextView tvData;
    private ImageView ivImage;
    int HOMEPAGE = 0;
    private int ORDER = 1;
    private int DEAL = 2;
    private int DATA = 3;
    private View vHomePage;
    private View vOrder;
    private View vDeal;
    private View vData;
    private TextView tvCompany;
    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvVolume;
    private Shop shop;
    private Login user;
    private Volume volume;
    private ImageView ivPop;
    private ShopHomeFragment mShopHomeFragment;
    private MyApplyFragment mMyApplyFragment;
    private ExampleFragment mExampleFragment;
    private ShopInformationFragment mShopInformationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        initView();
        initData();
        initEvents();
        selectTab(HOMEPAGE);//初始化fragment
    }

    private void initData() {
        /**
         * 要从服务端获取完数据后，更新UI(主线程不能调用子线程数据???)
         */
        myShopMessage();
    }

    private void myShopMessage() {
        String url = Constants.BASE_URL + "/applyShopServlet";
        String userName = SpUtils.getTokenId(this,Constants.TOKENID);
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
            shop.setProvince(obj.getString("province"));
            shop.setCity(obj.getString("city"));
            shop.setNature(obj.getString("nature"));
        }
//        Log.e("TAG", "parseSHOPWithGSON: " + shop.getCompany() );
        for (int i = 0; i < userArray.length(); i++) {
            user = new Login();
            JSONObject obj = userArray.getJSONObject(i);
            user.setHead(obj.getString("head"));
        }
        for (int i = 0; i < volumeArray.length(); i++) {
            volume = new Volume();
            JSONObject obj = volumeArray.getJSONObject(i);
            volume.setSum(obj.getString("volume"));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(getBaseContext())
                        .load(Constants.BASE_URL + SpUtils.getHead(getBaseContext(),Constants.HEAD))
                        .into(ivImage);
                tvCompany.setText(shop.getCompany());
                tvProvince.setText(shop.getProvince());
                tvCity.setText(shop.getCity());
                tvVolume.setText(volume.getSum() + "笔");
            }
        });
    }

    private void initEvents() {
        tvHomePage.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        tvDeal.setOnClickListener(this);
        tvData.setOnClickListener(this);
        ivPop.setOnClickListener(this);
    }

    private void initView() {
        ivImage = (ImageView) findViewById(R.id.iv_image);
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvProvince = (TextView) findViewById(R.id.tv_province);
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvVolume = (TextView) findViewById(R.id.tv_volume);
        ivPop = (ImageView) findViewById(R.id.iv_pop);
        //四个指示器控件
        tvHomePage = (TextView) findViewById(R.id.tv_homePage);
        tvOrder = (TextView) findViewById(R.id.tv_order);
        tvDeal = (TextView) findViewById(R.id.tv_deal);
        tvData = (TextView) findViewById(R.id.tv_data);
        vHomePage = findViewById(R.id.v_homePage);
        vOrder = findViewById(R.id.v_order);
        vData = findViewById(R.id.v_data);
        vDeal = findViewById(R.id.v_deal);
    }

    @Override
    public void onClick(View v) {
        resetTab();//初始化字体颜色
        switch (v.getId()){
            case R.id.tv_homePage:
                selectTab(HOMEPAGE);
                break;
            case R.id.tv_order:
                selectTab(ORDER);
                break;
            case R.id.tv_deal:
                selectTab(DEAL);
                break;
            case R.id.tv_data:
                selectTab(DATA);
                break;
            case R.id.iv_pop:
                AddPopWindow addPopWindow = new AddPopWindow(this);
                addPopWindow.showPopupWindow(ivPop);
                break;
        }
    }

    private void selectTab(int i) {
        //获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的fragment
        hideFragments(transaction);
        switch (i) {
            case 0:
                tvHomePage.setTextColor(this.getResources().getColor(R.color.text_orange));
                vHomePage.setBackgroundColor(this.getResources().getColor(R.color.text_orange));
                //如果对应的Fragment没有实例化，则进行实例化，并显示出来
                if (mShopHomeFragment == null) {
                    mShopHomeFragment = new ShopHomeFragment();
                    transaction.add(R.id.content, mShopHomeFragment);
                } else {
                    //如果对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mShopHomeFragment);
                }
                break;
            case 1:
                tvOrder.setTextColor(this.getResources().getColor(R.color.text_orange));
                vOrder.setBackgroundColor(this.getResources().getColor(R.color.text_orange));
                if (mMyApplyFragment == null) {
                    mMyApplyFragment = new MyApplyFragment();
                    transaction.add(R.id.content, mMyApplyFragment);
                } else {
                    transaction.show(mMyApplyFragment);
                }
                break;
            case 2:
                tvDeal.setTextColor(this.getResources().getColor(R.color.text_orange));
                vDeal.setBackgroundColor(this.getResources().getColor(R.color.text_orange));
                if (mExampleFragment == null) {
                    mExampleFragment = new ExampleFragment();
                    transaction.add(R.id.content, mExampleFragment);
                } else {
                    transaction.show(mExampleFragment);
                }
                break;
            case 3:
                tvData.setTextColor(this.getResources().getColor(R.color.text_orange));
                vData.setBackgroundColor(this.getResources().getColor(R.color.text_orange));
                if (mShopInformationFragment == null) {
                    mShopInformationFragment = new ShopInformationFragment();
                    transaction.add(R.id.content, mShopInformationFragment);
                } else {
                    transaction.show(mShopInformationFragment);
                }
                break;
        }
        //不要忘记提交事务
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mShopHomeFragment!= null) {
            transaction.hide(mShopHomeFragment);
        }
        if (mMyApplyFragment != null) {
            transaction.hide(mMyApplyFragment);
        }
        if (mExampleFragment != null) {
            transaction.hide(mExampleFragment);
        }
        if (mShopInformationFragment != null) {
            transaction.hide(mShopInformationFragment);
        }
    }

    private void resetTab() {
        tvHomePage.setTextColor(this.getResources().getColor(R.color.text_gray));
        tvOrder.setTextColor(this.getResources().getColor(R.color.text_gray));
        tvDeal.setTextColor(this.getResources().getColor(R.color.text_gray));
        tvData.setTextColor(this.getResources().getColor(R.color.text_gray));
        vHomePage.setBackgroundColor(this.getResources().getColor(R.color.back_gray));
        vOrder.setBackgroundColor(this.getResources().getColor(R.color.back_gray));
        vDeal.setBackgroundColor(this.getResources().getColor(R.color.back_gray));
        vData.setBackgroundColor(this.getResources().getColor(R.color.back_gray));
    }
}
