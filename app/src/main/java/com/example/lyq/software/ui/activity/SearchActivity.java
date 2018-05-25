package com.example.lyq.software.ui.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.base.ServerActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.ui.adapter.StorageAdapter;
import com.example.lyq.software.utils.HttpUtil;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private EditText etSearch;
    private LinearLayout llSubmit;
    private LinearLayout llHistory;
    private TextView tvCancel;
    private TextView tvSubmit;
    private String value;
    private ContentResolver contentResolver;
    private ListView lvStorage;
    private ImageView ivDelete;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //设置手机的屏幕旋转不触发
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //注意contentResolver不能为空
        contentResolver = getContentResolver();
        initView();
        initEvents();
        initData();
    }

    private void initView() {
        etSearch = (EditText) findViewById(R.id.et_search);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        llSubmit = (LinearLayout) findViewById(R.id.ll_submit);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        lvStorage = (ListView) findViewById(R.id.lv_storage);
        ivDelete = (ImageView) findViewById(R.id.iv_delete);
        llHistory = (LinearLayout) findViewById(R.id.ll_history);
        lvStorage.setDividerHeight(1);//设置listView分割线的高度
        //Android中ListView显示底部的分割线,需要同事满足ListView的高度和其父布局的高度都必须为match_parent。
        StorageAdapter adapter = new StorageAdapter(SearchActivity.this,R.layout.list_save_search,list);
        lvStorage.setAdapter(adapter);

        /**
         * EditText中有输入值时设置的监听
         */
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                llHistory.setVisibility(View.VISIBLE);
                llSubmit.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                llHistory.setVisibility(View.GONE);
                llSubmit.setVisibility(View.VISIBLE);
                value = etSearch.getText().toString();
                tvSubmit.setText(value);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * ListView的点击事件
         */
        lvStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                value = list.get(position);
                Intent intent = new Intent();
                intent.putExtra("value",value);
                intent.setClass(SearchActivity.this,SearchResultActivity.class);
                startActivity(intent);
                Toast.makeText(SearchActivity.this, "value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initEvents() {
        tvCancel.setOnClickListener(this);
        llSubmit.setOnClickListener(this);
        llHistory.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
    }

    private void initData() {
        Cursor cursor = contentResolver.query(Uri.parse("content://com.jiding.content"),null,null,null,null);
        String content = "";
        while (cursor.moveToNext()){
            String value = cursor.getString(1);
            list.add(value);
            content = content + value + "  " + "\n";
        }
        if (!TextUtils.isEmpty(content)){
            llHistory.setVisibility(View.VISIBLE);
        } else {
            llHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                this.finish();
                break;
            case R.id.ll_submit:
                ContentValues contentValues = new ContentValues();
                contentValues.put("value",value);
                contentResolver.insert(Uri.parse("content://com.jiding.content"),contentValues);
                Intent intent = new Intent();
                intent.putExtra("value",value);
                intent.setClass(SearchActivity.this,SearchResultActivity.class);
                startActivity(intent);
                Toast.makeText(this,"数据插入成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_delete:
                contentResolver.delete(Uri.parse("content://com.jiding.content"),null,null);
                //刷新当前activity
                refresh();
                Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void refresh() {
        onCreate(null);
    }
}
