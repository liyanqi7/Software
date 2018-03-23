package com.example.lyq.software.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.ui.adapter.TypeTwoAdapter;
import com.example.lyq.software.ui.bean.TypeTwo;

import java.util.ArrayList;
import java.util.List;

public class TypeTwoActivity extends BaseActivity {
    private String type;
    private List<TypeTwo> typeTwoList = new ArrayList<>();
    String TAG = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_two);
        Intent intent = getIntent();//用getIntent来获取从上一个页面Intent携带的参数
        type = intent.getStringExtra("type");
        initData();
        ListView listView = (ListView) findViewById(R.id.list_view);
        Log.e(TAG, "onCreate: "+type );
        TypeTwoAdapter adapter = new TypeTwoAdapter(TypeTwoActivity.this,R.layout.item_list_type_two,typeTwoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TypeTwo typeTwo = typeTwoList.get(position);
                Intent intent = new Intent();
                intent.putExtra("typeTwo",typeTwo.getTypeTwo());
                TypeTwoActivity.this.setResult(RESULT_OK,intent);
                finish();//不需要getParent().finish;
            }
        });
    }

    private void initData() {
        if (type .equals("网站建设")){
            Log.e(TAG, "initData: "+type);
            TypeTwo type1 = new TypeTwo("网站定制");
            typeTwoList.add(type1);
            TypeTwo type2 = new TypeTwo("模板建站");
            typeTwoList.add(type2);
            TypeTwo type3 = new TypeTwo("手机网站");
            typeTwoList.add(type3);
            TypeTwo type4 = new TypeTwo("前端开发");
            typeTwoList.add(type4);
            TypeTwo type5 = new TypeTwo("教育网站");
            typeTwoList.add(type5);
            TypeTwo type6 = new TypeTwo("企业官网");
            typeTwoList.add(type6);
            TypeTwo type7 = new TypeTwo("电商网站");
            typeTwoList.add(type7);
            TypeTwo type8 = new TypeTwo("网站维护");
            typeTwoList.add(type8);
        }
        if (type .equals("APP开发")){
            TypeTwo type1 = new TypeTwo("商城APP");
            typeTwoList.add(type1);
            TypeTwo type2 = new TypeTwo("点餐APP");
            typeTwoList.add(type2);
            TypeTwo type3 = new TypeTwo("社交APP");
            typeTwoList.add(type3);
            TypeTwo type4 = new TypeTwo("直播APP");
            typeTwoList.add(type4);
            TypeTwo type5 = new TypeTwo("金融理财");
            typeTwoList.add(type5);
            TypeTwo type6 = new TypeTwo("在线教育");
            typeTwoList.add(type6);
            TypeTwo type7 = new TypeTwo("智能家居");
            typeTwoList.add(type7);
            TypeTwo type8 = new TypeTwo("智能社区");
            typeTwoList.add(type8);
        }
        if(type .equals("微信开发")){
            TypeTwo type1 = new TypeTwo("小程序开发");
            typeTwoList.add(type1);
            TypeTwo type2 = new TypeTwo("微信商城");
            typeTwoList.add(type2);
            TypeTwo type3 = new TypeTwo("微信餐饮");
            typeTwoList.add(type3);
            TypeTwo type4 = new TypeTwo("微信分销");
            typeTwoList.add(type4);
            TypeTwo type5 = new TypeTwo("微官网");
            typeTwoList.add(type5);
            TypeTwo type6 = new TypeTwo("微投票");
            typeTwoList.add(type6);
            TypeTwo type7 = new TypeTwo("微活动");
            typeTwoList.add(type7);
            TypeTwo type8 = new TypeTwo("H5定制");
            typeTwoList.add(type8);
        }
        if (type .equals("UI设计")){
            TypeTwo type1 = new TypeTwo("网页设计");
            typeTwoList.add(type1);
            TypeTwo type2 = new TypeTwo("APP界面");
            typeTwoList.add(type2);
            TypeTwo type3 = new TypeTwo("banner设计");
            typeTwoList.add(type3);
            TypeTwo type4 = new TypeTwo("icon设计");
            typeTwoList.add(type4);
            TypeTwo type5 = new TypeTwo("单页设计");
            typeTwoList.add(type5);
            TypeTwo type6 = new TypeTwo("整套设计");
            typeTwoList.add(type6);
            TypeTwo type7 = new TypeTwo("交互设计");
            typeTwoList.add(type7);
            TypeTwo type8 = new TypeTwo("框架设计");
            typeTwoList.add(type8);
        }
        if (type .equals("H5开发")){
            TypeTwo type1 = new TypeTwo("H5开发");
            typeTwoList.add(type1);
        }
        if (type.equals("解决方案")){
            TypeTwo type1 = new TypeTwo("解决方案");
            typeTwoList.add(type1);
        }
        if (type .equals("数据服务")){
            TypeTwo type1 = new TypeTwo("数据服务");
            typeTwoList.add(type1);
        }
        if (type .equals("安全服务")){
            TypeTwo type1 = new TypeTwo("安全服务");
            typeTwoList.add(type1);
        }
        if (type .equals("测试服务")){
            TypeTwo type1 = new TypeTwo("测试服务");
            typeTwoList.add(type1);
        }
        if (type .equals("软件SaaS")){
            TypeTwo type1 = new TypeTwo("软件SaaS");
            typeTwoList.add(type1);
        }
        if (type .equals("域名服务")){
            TypeTwo type1 = new TypeTwo("域名注册");
            typeTwoList.add(type1);
            TypeTwo type2 = new TypeTwo("域名备案");
            typeTwoList.add(type2);
            TypeTwo type3 = new TypeTwo("域名解析");
            typeTwoList.add(type3);
            TypeTwo type4 = new TypeTwo("域名空间套餐");
            typeTwoList.add(type4);
        }
        if (type .equals("云服务")){
            TypeTwo type1 = new TypeTwo("服务器托管");
            typeTwoList.add(type1);
            TypeTwo type2 = new TypeTwo("云主机");
            typeTwoList.add(type2);
            TypeTwo type3 = new TypeTwo("环境部署");
            typeTwoList.add(type3);
            TypeTwo type4 = new TypeTwo("云服务");
            typeTwoList.add(type4);
        }
        if (type .equals("其他服务")){
            TypeTwo type1 = new TypeTwo("其他服务");
            typeTwoList.add(type1);
        }
    }
}
