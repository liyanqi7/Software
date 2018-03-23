package com.example.lyq.software.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.bean.Register;
import com.example.lyq.software.utils.HttpUtil;
import com.example.lyq.software.utils.SpUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.lyq.software.lib.Constants.BASE_URL;

public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private LinearLayout btnNick;
    private View nickView;
    private TextView cancel;
    private TextView confirm;
    private AlertDialog nickDialog;
    private Dialog pwdDialog;
    private LinearLayout btnPwd;
    private View pwdView;
    private EditText changeNick;
    String url = BASE_URL + "/changeNickServlet";
    private TextView tvNick;
    private String tokenId;
    private String nick;
    private static String path = "/sdcard/DemoHead/";
    private String fileName;
    private LinearLayout llPick;
    public Context context;
    private CircleImageView ivHead;
    String newNick;
    String TAG = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        tokenId = SpUtils.getTokenId(this, Constants.TOKENID);
        nick = SpUtils.getNick(this, Constants.NICK);
        back = (ImageView) findViewById(R.id.back);
        btnNick = (LinearLayout) findViewById(R.id.btn_nick);
        btnPwd = (LinearLayout) findViewById(R.id.btn_pwd);
        tvNick = (TextView) findViewById(R.id.tv_nick);
        llPick = (LinearLayout) findViewById(R.id.ll_pick);
        ivHead = (CircleImageView) findViewById(R.id.iv_head);
        nickView = LayoutInflater.from(this).inflate(R.layout.item_modify_nick,null,false);
        pwdView = LayoutInflater.from(this).inflate(R.layout.item_modify_pwd,null,false);
        back.setOnClickListener(this);
        btnNick.setOnClickListener(this);
        btnPwd.setOnClickListener(this);
        llPick.setOnClickListener(this);
        String head = SpUtils.getHead(getBaseContext(),Constants.HEAD).toString();
        if (!head.isEmpty()){
            Glide.with(getBaseContext())
                    .load(BASE_URL + head)
                    .into(ivHead);
        }
        initMessage();
        initNickDialog();
        initPwdDialog();
    }

    private void initMessage() {
        if (!tokenId.isEmpty()){
            tvNick.setText(nick);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                break;
            case R.id.btn_nick:
                nickDialog.show();
                break;
            case R.id.btn_pwd:
                pwdDialog.show();
                break;
            case R.id.ll_pick:
                setBottomDialog();
                break;
        }
    }

    private void setBottomDialog() {
        final Dialog mBottomDialog = new Dialog(this,R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_bottom_dialog, null);
        TextView tvTakePhoto = root.findViewById(R.id.tv_takePhoto);
        TextView tvAlbum = root.findViewById(R.id.tv_album);
        TextView tvCancel = root.findViewById(R.id.tv_cancel);
        mBottomDialog.setContentView(root);
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent();
                cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.jpg")));//指明存储图片的地址URI
                startActivityForResult(cameraIntent, Constants.CAMERA);
                mBottomDialog.dismiss();
            }
        });
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK,null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(albumIntent, Constants.ALBUM);
                mBottomDialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomDialog.dismiss();
            }
        });
//        Bitmap bt = BitmapFactory.decodeFile(fileName);//从本地获取图片
//        if (bt != null){
//            ivHead.setImageBitmap(bt);
//        }

        Window dialogWindow = mBottomDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mBottomDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.CAMERA:
                if (resultCode == RESULT_OK){
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));
                }
                break;
            case Constants.ALBUM:
                if (resultCode == RESULT_OK){
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case Constants.CROP_PHOTO:
                if (data != null){
                    Bundle extras = data.getExtras();
                    Bitmap head = extras.getParcelable("data");
                    if (head != null){
                        setPicToView(head);//将裁剪好的图片放入指定的文件夹中
                        final String imagePath = Uri.decode(fileName);
                        uploadImage(imagePath);//上传至服务器
                    }
                }
                break;
        }
    }

    /**
     * 上传图片
     * @param imagePath
     */
    private void uploadImage(String imagePath) {
        new NetworkTask().execute(imagePath);
    }

    /**
     * 访问网络AsyncTask,访问网络在子线程进行并返回主线程通知访问的结果
     */
    class NetworkTask extends AsyncTask<String, Integer, String> {

        //将在执行实际的后台操作前被UI 线程调用
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //onPreExecute()方法执行后马上执行，该方法运行在后台线程中
        @Override
        protected String doInBackground(String... params) {
            return doPost(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if(!"error".equals(result)) {
                SpUtils.putHead(getBaseContext(),Constants.HEAD,result);
                Log.i(TAG, "图片地址 " + BASE_URL + result);
                Glide.with(getBaseContext())
                        .load(BASE_URL + result)
                        .into(ivHead);
            }
        }
    }

    private String doPost(String imagePath) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String result = "error";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        // 这里演示添加用户ID
        builder.addFormDataPart("tokenId", tokenId);
        builder.addFormDataPart("image", imagePath,
                RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath)));
        RequestBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        Request request = reqBuilder
                .url(Constants.BASE_URL + "/changeHeadServlet")
                .post(requestBody)
                .build();
        Log.d(TAG, "请求地址 " + Constants.BASE_URL + "/changeHeadServlet");
        try{
            Response response = mOkHttpClient.newCall(request).execute();
            Log.d(TAG, "响应码 " + response.code());
            if (response.isSuccessful()) {
                String resultValue = response.body().string();
                Log.d(TAG, "响应体 " + resultValue);
                return resultValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop",true);
        //aspectX aspectY是
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,Constants.CROP_PHOTO);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStaus = Environment.getExternalStorageState();
        if (!sdStaus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();
        fileName = path + "head.jpg";
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);//把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initNickDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(nickView);
        nickDialog = builder.create();
        cancel = (TextView) nickView.findViewById(R.id.cancel);
        confirm = (TextView) nickView.findViewById(R.id.confirm);
        changeNick = (EditText) nickView.findViewById(R.id.nick);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickDialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNick = changeNick.getText().toString().trim();
                String tokenId = SpUtils.getTokenId(getBaseContext(), Constants.TOKENID);
                RequestBody body = new FormBody.Builder()//以form表单的形式发送数据
                        .add("tokenId",tokenId)
                        .add("nick",newNick)
                        .build();

                HttpUtil.post(url, body, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        parseJSONWithGSON(responseData);
                    }
                });
                nickDialog.dismiss();
            }
        });
    }

    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        Register rs = gson.fromJson(responseData, Register.class);
        if (rs.getResult().toString().equals("true")){
            SpUtils.putNick(getBaseContext(),Constants.NICK,newNick);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refresh();
                    initMessage();
                    Toast.makeText(PersonalActivity.this, "昵称修改成功!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(PersonalActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void refresh() {
        finish();
        Intent intent = new Intent(PersonalActivity.this, PersonalActivity.class);
        startActivity(intent);
    }

    private void initPwdDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(pwdView);
        pwdDialog = builder.create();
        cancel = (TextView) pwdView.findViewById(R.id.cancel);
        confirm = (TextView) pwdView.findViewById(R.id.confirm);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdDialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
