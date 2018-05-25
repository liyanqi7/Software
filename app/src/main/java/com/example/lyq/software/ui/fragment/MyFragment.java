package com.example.lyq.software.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.activity.CreateShopActivity;
import com.example.lyq.software.ui.activity.LoginActivity;
import com.example.lyq.software.ui.activity.MyApplyActivity;
import com.example.lyq.software.ui.activity.MyCollectionActivity;
import com.example.lyq.software.ui.activity.MyFollowActivity;
import com.example.lyq.software.ui.activity.MyShopActivity;
import com.example.lyq.software.ui.activity.MyUploadActivity;
import com.example.lyq.software.ui.activity.PersonalActivity;
import com.example.lyq.software.ui.activity.SetActivity;
import com.example.lyq.software.ui.bean.Login;
import com.example.lyq.software.ui.bean.Register;
import com.example.lyq.software.ui.bean.Result;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.lyq.software.lib.Constants.BASE_URL;

public class MyFragment extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout head;
    private ImageView set;
    private TextView name;
    private CircleImageView ivHead;
    private LinearLayout llMyRunning;
    private LinearLayout llMyShop;
    private LinearLayout llMyCollection;
    private LinearLayout llAttention;
    private LinearLayout llMyFinish;
    private LinearLayout llMyUpload;
    private String tokenId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        initView();
        return view;
    }

    private void initView() {
        head = (LinearLayout) view.findViewById(R.id.head);
        set = (ImageView) view.findViewById(R.id.set);
        name = (TextView) view.findViewById(R.id.username);
        ivHead = (CircleImageView) view.findViewById(R.id.iv_head);
        llMyUpload = (LinearLayout) view.findViewById(R.id.ll_myUpload);
        llMyRunning= (LinearLayout) view.findViewById(R.id.ll_myRunning);
        llMyFinish = (LinearLayout) view.findViewById(R.id.ll_myFinish);
        llMyShop = (LinearLayout) view.findViewById(R.id.ll_myShop);
        llMyCollection = (LinearLayout) view.findViewById(R.id.ll_myCollection);
        llAttention = (LinearLayout) view.findViewById(R.id.ll_attention);
        head.setOnClickListener(this);
        set.setOnClickListener(this);
        llMyUpload.setOnClickListener(this);
        llMyRunning.setOnClickListener(this);
        llMyFinish.setOnClickListener(this);
        llMyShop.setOnClickListener(this);
        llMyCollection.setOnClickListener(this);
        llAttention.setOnClickListener(this);
    }

    //注意:Fragment的生命周期,onCreateView(),onResume()
    @Override
    public void onResume() {
        super.onResume();
        tokenId = SpUtils.getTokenId(getContext(), Constants.TOKENID);
        String head = SpUtils.getHead(getContext(),Constants.HEAD);
        Log.e("1", "onCreateView: " + tokenId);
        Log.e("1", "head: " + head);
        if (!tokenId.toString().isEmpty())
        {
            String nick = SpUtils.getNick(getContext(),Constants.NICK);
            name.setText(nick);
        }else {
            name.setText("请先注册或/登录");
        }
        //如果上传了头像，显示头像
        if (!head.toString().isEmpty()){
            Glide.with(getContext())
                    .load(BASE_URL + head)
                    .into(ivHead);
        }else {
            ivHead.setImageResource(R.mipmap.icon_login);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head:
                String tokenId = SpUtils.getTokenId(getContext(), Constants.TOKENID);
                if (tokenId.toString().isEmpty())
                {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                }else {
                    startActivity(new Intent(getContext(), PersonalActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                }
                break;
            case R.id.set:
                startActivity(new Intent(new Intent(getContext(), SetActivity.class)));
                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            case R.id.ll_myUpload:
                Intent uploadIntent = new Intent(getContext(), MyUploadActivity.class);
                uploadIntent.putExtra("type","正在发布");
                uploadIntent.putExtra("state","发布中");
                startActivity(uploadIntent);
                break;
            case R.id.ll_myRunning:
                Intent runningIntent = new Intent(getContext(), MyUploadActivity.class);
                runningIntent.putExtra("type","案例进行");
                runningIntent.putExtra("state","任务中");
                startActivity(runningIntent);
                break;
            case R.id.ll_myFinish:
                Intent finishIntent = new Intent(getContext(), MyUploadActivity.class);
                finishIntent.putExtra("type","完成案列");
                finishIntent.putExtra("state","已完成");
                startActivity(finishIntent);
                break;
            case R.id.ll_myShop:
                doJudgeShop();
                break;
            case R.id.ll_myCollection:
                Intent collectionIntent = new Intent(getContext(), MyCollectionActivity.class);
                collectionIntent.putExtra("type","我的收藏");
                startActivity(collectionIntent);
                break;
            case R.id.ll_attention:
                Intent attentionIntent = new Intent(getContext(), MyFollowActivity.class);
                attentionIntent.putExtra("type","我的关注");
                startActivity(attentionIntent);
                break;
        }
    }

    private void doJudgeShop() {
        String url = Constants.BASE_URL + "/judgeShopServlet";
        FormBody body = new FormBody.Builder()
                .add("userName", tokenId)
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
//                Log.e("TAG", "onResponse: "+rs.getResult() );
                if (rs.getResult().toString().equals("true")){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent shopIntent = new Intent(getContext(), MyShopActivity.class);
                            startActivity(shopIntent);
                        }
                    });
                }else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent createIntent = new Intent(getContext(), CreateShopActivity.class);
                            startActivity(createIntent);
                        }
                    });
                }
            }
        });;
    }
}
