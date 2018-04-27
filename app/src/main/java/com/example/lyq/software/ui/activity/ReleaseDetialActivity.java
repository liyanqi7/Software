package com.example.lyq.software.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Images;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Register;
import com.example.lyq.software.ui.bean.Release;
import com.example.lyq.software.ui.bean.Result;
import com.example.lyq.software.utils.DateTimeUtil;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReleaseDetialActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView ciHead;
    private TextView tvNick;
    private TextView tvType;
    private TextView tvDescript;
    private Release release;
    private Images images;
    private Login user;
    private TextView tvPrice;
    private TextView tvBegin;
    private TextView tvEnd;
    private LinearLayout llAply;
    private TextView tvApply;
    private String userName;
    private TextView tvDate;
    private LinearLayout llCollection;
    private ImageView ivCollection;
    private TextView tvCollection;
    private String collectionState;
    private String state;
    private TextView tvConfirm;
    private TextView tvCancel;
    private LinearLayout llUpload;
    private LinearLayout llCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_detial);
        release = (Release) getIntent().getSerializableExtra("releaseData");//接收对象参数的传递
        images = (Images) getIntent().getSerializableExtra("imageData");
        user = (Login) getIntent().getSerializableExtra("userData");
        state = getIntent().getStringExtra("state");
        userName = SpUtils.getTokenId(getBaseContext(), Constants.TOKENID);
        Log.e("user", "onCreate: " + userName);
        if (!(userName.isEmpty())){ //判断是否为空，不能用(userName == null)
            doJudgeShop();//判断是否有店铺，决定用户的申请权限
//            doJudgeApply();
            doJudgeCollection();//判断订单是否被收藏,初始化collectionState的值
        }
        initView();
        initState();
    }

    private void initState() {
        llUpload.setVisibility(View.GONE);
        tvCancel.setVisibility(View.GONE);
        tvConfirm.setVisibility(View.GONE);
        if (state.equals("任务中")){
            tvConfirm.setVisibility(View.VISIBLE);
        }
        if (state.equals("upload")){
            llUpload.setVisibility(View.VISIBLE);
        }
        if (state.equals("发布中")){
            tvCancel.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        llUpload = (LinearLayout) findViewById(R.id.ll_upload);
        tvType = (TextView) findViewById(R.id.tv_type);
        ciHead = (CircleImageView) findViewById(R.id.ci_head);
        tvNick = (TextView) findViewById(R.id.tv_nick);
        tvDescript = (TextView) findViewById(R.id.tv_descript);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvBegin = (TextView) findViewById(R.id.tv_begin);
        tvEnd = (TextView) findViewById(R.id.tv_end);
        llCreate = (LinearLayout) findViewById(R.id.ll_create);
        llAply = (LinearLayout) findViewById(R.id.ll_apply);
        tvApply = (TextView) findViewById(R.id.tv_apply);
        llAply.setOnClickListener(this);
        llCollection = (LinearLayout) findViewById(R.id.ll_collection);
        ivCollection = (ImageView) findViewById(R.id.iv_collection);
        tvCollection = (TextView) findViewById(R.id.tv_collection);
        llCollection.setOnClickListener(this);
        ImageView image1 = (ImageView) findViewById(R.id.image1);
        ImageView image2 = (ImageView) findViewById(R.id.image2);
        ImageView image3 = (ImageView) findViewById(R.id.image3);
        ImageView image4 = (ImageView) findViewById(R.id.image4);
        ImageView image5 = (ImageView) findViewById(R.id.image5);
        ImageView image6 = (ImageView) findViewById(R.id.image6);
        ImageView image7 = (ImageView) findViewById(R.id.image7);
        ImageView image8 = (ImageView) findViewById(R.id.image8);
        ImageView image9 = (ImageView) findViewById(R.id.image9);
        Glide.with(this)
                .load(Constants.BASE_URL + user.getHead())
                .into(ciHead);
        tvNick.setText(user.getNick());
        tvType.setText(release.getType());
        tvPrice.setText(release.getPrice());
        tvDescript.setText(release.getDescript());
        Date date = DateTimeUtil.strToDateHHMMSS(release.getDate());
        String timeLength = DateTimeUtil.formatFriendly(date);
        tvDate.setText(timeLength);
        tvBegin.setText(release.getBeginTime());
        tvEnd.setText(release.getEndTime());
        Glide.with(this)
                .load(Constants.BASE_URL + images.getImage1())
                .into(image1);
        Glide.with(this)
                .load(Constants.BASE_URL + images.getImage2())
                .into(image2);
        Glide.with(this)
                .load(Constants.BASE_URL + images.getImage3())
                .into(image3);
        Glide.with(this)
                .load(Constants.BASE_URL + images.getImage4())
                .into(image4);
        Glide.with(this)
                .load(Constants.BASE_URL + images.getImage5())
                .into(image5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_apply:
                if (!userName.isEmpty()){
                    doApply();
                }else {
                    this.finish();
                    startActivity(new Intent(this,LoginActivity.class));
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                    Toast.makeText(this, "请先登录在申请...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_collection:
                if (!userName.isEmpty()){
//                    doJudgeCollection();
                    if (collectionState.equals("true")){
                        doDeleteCollection();
                    } else {
                        doCollection();
                    }
                }else {
                    this.finish();
                    startActivity(new Intent(this,LoginActivity.class));
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                    Toast.makeText(this, "请先登录在收藏...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 取消收藏
     */
    private void doDeleteCollection() {
        String url = Constants.BASE_URL + "/deleteCollectionServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("releaseId",release.getReleaseId())
                .add("userName",userName)
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        collectionState = "false";
                        ivCollection.setImageResource(R.mipmap.icon_collection);
                        tvCollection.setText("收藏");
                        tvCollection.setTextColor(getResources().getColor(R.color.text_gray));
                    }
                });
            }
        });
    }

    /**
     * 收藏订单
     */
    private void doCollection() {
        String url = Constants.BASE_URL + "/insertCollectionServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("releaseId",release.getReleaseId())
                .add("userName",userName)
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Result rs = gson.fromJson(responseData, Result.class);
                if (rs.getResult().toString().equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            collectionState = "true";
                            ivCollection.setImageResource(R.mipmap.icon_collection_change);
                            tvCollection.setText("取消收藏");
                            tvCollection.setTextColor(getResources().getColor(R.color.text_orange));
                        }
                    });
                }
            }
        });
    }

    /**
     * 判断订单是否已经被收藏
     */
    private void doJudgeCollection() {
        String url = Constants.BASE_URL + "/judgeCollectionServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("releaseId",release.getReleaseId())
                .add("userName",userName)
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Result rs = gson.fromJson(responseData, Result.class);
                collectionState = rs.getResult().toString();
                if (collectionState.equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivCollection.setImageResource(R.mipmap.icon_collection_change);
                            tvCollection.setText("取消收藏");
                            tvCollection.setTextColor(getResources().getColor(R.color.text_orange));
                        }
                    });
                }
            }
        });
    }

    /**
     * 判断用户是否是可接单用户
     */
    private void doJudgeShop() {
        String url = Constants.BASE_URL + "/judgeShopServlet";
        String userName = SpUtils.getTokenId(getBaseContext(),Constants.TOKENID);
        FormBody body = new FormBody.Builder()
                .add("userName",userName)
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//注意:response.body().string();不是response.body().toString(),sting()通过使用指定的charset解码指定的byte数组，构造一个新的String
                Gson gson = new Gson();
                Result rs = gson.fromJson(responseData, Result.class);
                if (rs.getResult().toString().equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            llAply.setVisibility(View.VISIBLE);
                            llCreate.setVisibility(View.GONE);
                            doJudgeApply();//判断订单是被否申请
                        }
                    });
                }else {
                    ReleaseDetialActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            llAply.setVisibility(View.GONE);
                            llCreate.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    /**
     * 判断订单用户是否已经被申请
     */
    private void doJudgeApply() {
        String url = Constants.BASE_URL + "/judgeApplyServlet";
        RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                .add("releaseId",release.getReleaseId())
                .add("applyName",userName)
                .build();
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Result rs = gson.fromJson(responseData, Result.class);
                if (rs.getResult().toString().equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            llAply.setEnabled(false);
                            tvApply.setText("已申请");
                        }
                    });
                }
            }
        });
    }

    /**
     * 申请订单任务
     */
    private void doApply() {
        JSONObject object = new JSONObject();
        String releaseId = release.getReleaseId();
        String descript = release.getDescript();
        String uploadName = release.getUserName();
        String head = user.getHead();
        String date = DateTimeUtil.getSWAHDate();
        String browse = "false";
        Log.e("date", "doApply: " + date );
        try {
            object.put("releaseId",releaseId);
            object.put("uploadName",uploadName);
            object.put("applyName",userName);
            object.put("descript",descript);
            object.put("head",head);
            object.put("date",date);
            object.put("browse",browse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String content = String.valueOf(object);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),content);//json格式发送
        String url = Constants.BASE_URL + "/insertOrderServlet";
        HttpUtil.post(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("code", "onResponse: " + response.code());
                String responseData = response.body().string();
                parseJSONWithGSON(responseData);
            }
        });
    }

    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        Result rs = gson.fromJson(responseData, Result.class);
        if (rs.getResult().toString().equals("true")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    llAply.setEnabled(false);
                    tvApply.setText("已申请");
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ReleaseDetialActivity.this, "申请失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
